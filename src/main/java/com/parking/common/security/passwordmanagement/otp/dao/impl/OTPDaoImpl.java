/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.passwordmanagement.otp.dao.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.parking.common.dao.impl.GenericDaoImpl;
import com.parking.common.security.passwordmanagement.otp.OTPStatus;
import com.parking.common.security.passwordmanagement.otp.dao.OTPDao;
import com.parking.common.security.passwordmanagement.otp.model.OneTimePassword;

@Repository("otpDao")
public class OTPDaoImpl extends GenericDaoImpl<OneTimePassword, String> implements OTPDao {
	@Autowired
	public OTPDaoImpl(
			@Value("com.pmcretail.framework.security.passwordmanagement.otp.model.OneTimePassword") Class<OneTimePassword> persistentClass) {
		super(persistentClass);
	}

	public int updateOTPStatus(String id, OTPStatus otpStatusType) {
		Map params = new HashMap();
		params.put("otpId", id);
		params.put("status", otpStatusType);
		return executeByQuery("update OneTimePassword otp set otp.status = :status where otp.id = :otpId", params);
	}

	public OneTimePassword checkUserAlreadyRequestedForOTP(String userId, Calendar otpCreatedDate) {
		Map params = new HashMap();
		params.put("status", OTPStatus.PENDING);
		params.put("userId", userId);
		params.put("otpCreatedDate", otpCreatedDate);

		List otps = findByNamedQuery("checkUserAlreadyRequestedForOTP", params);

		if ((otps != null) && (!(otps.isEmpty()))) {
			return ((OneTimePassword) otps.get(0));
		}

		return null;
	}
}