/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.passwordmanagement.otp.manager;

import com.parking.common.security.manager.GenericManager;
import com.parking.common.security.model.User;
import com.parking.common.security.passwordmanagement.otp.PasswordParams;
import com.parking.common.security.passwordmanagement.otp.model.OneTimePassword;

public abstract interface OTPManager extends GenericManager<OneTimePassword, String> {
	public abstract OneTimePassword generateOTP(User paramUser);

	public abstract OneTimePassword generateOTP(User paramUser, PasswordParams paramPasswordParams);

	public abstract OneTimePassword getOTP(String paramString);

	public abstract boolean verifyOTP(OneTimePassword paramOneTimePassword, String paramString);

	public abstract boolean verifyOTP(OneTimePassword paramOneTimePassword, String paramString,
			PasswordParams paramPasswordParams);
}