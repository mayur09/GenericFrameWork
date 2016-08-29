/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.passwordmanagement.otp.dao;

import java.util.Calendar;

import com.parking.common.dao.GenericDao;
import com.parking.common.security.passwordmanagement.otp.OTPStatus;
import com.parking.common.security.passwordmanagement.otp.model.OneTimePassword;

public abstract interface OTPDao extends GenericDao<OneTimePassword, String> {
	public abstract int updateOTPStatus(String paramString, OTPStatus paramOTPStatus);

	public abstract OneTimePassword checkUserAlreadyRequestedForOTP(String paramString, Calendar paramCalendar);
}