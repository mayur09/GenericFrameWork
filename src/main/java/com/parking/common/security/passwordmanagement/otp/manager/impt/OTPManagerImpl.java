/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.passwordmanagement.otp.manager.impt;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parking.common.dao.GenericDao;
import com.parking.common.security.manager.impl.GenericManagerImpl;
import com.parking.common.security.model.User;
import com.parking.common.security.passwordmanagement.otp.OTPStatus;
import com.parking.common.security.passwordmanagement.otp.PasswordParams;
import com.parking.common.security.passwordmanagement.otp.dao.OTPDao;
import com.parking.common.security.passwordmanagement.otp.manager.OTPManager;
import com.parking.common.security.passwordmanagement.otp.model.OneTimePassword;
import com.parking.common.security.utilities.CommonUtility;

@Service("otpManager")
public class OTPManagerImpl extends GenericManagerImpl<OneTimePassword, String> implements OTPManager {
	private static final Logger LOG = LogManager.getLogger(OTPManagerImpl.class.getName());

	@Autowired
	@Qualifier("messageSource")
	AbstractMessageSource messageSource;

	@Autowired
	public OTPManagerImpl(@Qualifier("otpDao") GenericDao<OneTimePassword, String> genericDao) {
		super(genericDao);
	}

	@Transactional
	public OneTimePassword generateOTP(User user) {
		return generateOTP(user, new PasswordParams(this.messageSource));
	}

	@Transactional
	public OneTimePassword generateOTP(User user, PasswordParams passwordParams) {
		LOG.info("generating OTP for :: " + user.getUserName());

		Calendar otpCreatedDate = Calendar.getInstance();
		OneTimePassword oneTimePwd = checkUserAlreadyRequestedForOTP(user.getId(),
				substractHoursFromCalender(otpCreatedDate, passwordParams.getPwdExpires()));

		String optPassword = CommonUtility.generateRandomAlphaNumericString(passwordParams.getPwdLength());

		if (oneTimePwd != null) {
			LOG.info(user.getUserName() + " already reaquested for OTP");
			oneTimePwd.setCreatedDate(Calendar.getInstance());
			oneTimePwd.setOtp(CommonUtility.encodeString(passwordParams.getDigestEncoderTypes(), optPassword));
			oneTimePwd.setPlainOTP(optPassword);
		} else {
			LOG.info(user.getUserName() + " fresh reaquested for OTP");
			oneTimePwd = new OneTimePassword();
			oneTimePwd.setId(null);
			oneTimePwd.setCreatedDate(Calendar.getInstance());
			oneTimePwd.setOtp(CommonUtility.encodeString(passwordParams.getDigestEncoderTypes(), optPassword));
			oneTimePwd.setPlainOTP(optPassword);
			oneTimePwd.setStatus(OTPStatus.PENDING);
			oneTimePwd.setUser(user);
		}

		this.genericDao.save(oneTimePwd);
		return oneTimePwd;
	}

	public OneTimePassword getOTP(String otpId) {
		return ((OneTimePassword) this.genericDao.get(otpId));
	}

	public boolean verifyOTP(OneTimePassword oneTimePwd, String enterdOTP) {
		return verifyOTP(oneTimePwd, enterdOTP, new PasswordParams(this.messageSource));
	}

	public boolean verifyOTP(OneTimePassword oneTimePwd, String enterdOTP, PasswordParams passwordParams) {
		LOG.info("verifing OTP for  :: " + oneTimePwd.getUser().getUserName());

		OTPStatus otpStatus = OTPStatus.PENDING;
		boolean updateOtpStatus = true;
		boolean otpVerified = false;

		if ((OTPStatus.VERIFIED.equals(oneTimePwd.getStatus())) || (OTPStatus.EXPIRED.equals(oneTimePwd.getStatus()))) {
			otpStatus = oneTimePwd.getStatus();
			updateOtpStatus = false;
		} else if ((OTPStatus.PENDING.equals(oneTimePwd.getStatus()))
				&& (isOTPExpired(oneTimePwd.getCreatedDate(), passwordParams.getPwdExpires()))) {
			otpStatus = OTPStatus.EXPIRED;
		} else {
			updateOtpStatus = false;
			if (oneTimePwd.getOtp()
					.equals(CommonUtility.encodeString(passwordParams.getDigestEncoderTypes(), enterdOTP))) {
				otpStatus = OTPStatus.VERIFIED;
				updateOtpStatus = true;
				otpVerified = true;
			}
		}

		if (updateOtpStatus) {
			oneTimePwd.setStatus(otpStatus);
			save(oneTimePwd);
			LOG.info("OTP validated and updated status successfully." + otpStatus);
		} else {
			LOG.info("Wrong OTP password OR OTP is already used OR expired");
		}

		return otpVerified;
	}

	private boolean isOTPExpired(Calendar otpCalender, int hours) {
		Calendar currentCal = Calendar.getInstance();
		otpCalender.add(10, hours);
		return currentCal.after(otpCalender);
	}

	private Calendar substractHoursFromCalender(Calendar calender, int hours) {
		calender.add(10, -hours);
		return calender;
	}

	@Transactional
	private int updateOTPStatus(String id, OTPStatus otpStatus) {
		return ((OTPDao) this.genericDao).updateOTPStatus(id, otpStatus);
	}

	private OneTimePassword checkUserAlreadyRequestedForOTP(String userId, Calendar otpCreatedDate) {
		return ((OTPDao) this.genericDao).checkUserAlreadyRequestedForOTP(userId, otpCreatedDate);
	}
}