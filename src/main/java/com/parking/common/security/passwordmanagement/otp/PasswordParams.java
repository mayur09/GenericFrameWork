/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.passwordmanagement.otp;

import java.io.Serializable;

import org.springframework.context.support.AbstractMessageSource;

import com.parking.common.security.DigestEncoderTypes;

public class PasswordParams implements Serializable {
	private static final long serialVersionUID = 1L;
	private int pwdLength;
	private int pwdExpires;
	private DigestEncoderTypes digestEncoderTypes = DigestEncoderTypes.MD5;

	public PasswordParams(AbstractMessageSource messageSource) {
		this.pwdLength = Integer.parseInt(messageSource.getMessage("otp.password.length", null, null));
		this.pwdExpires = Integer.parseInt(messageSource.getMessage("otp.password.expires", null, null));
	}

	public int getPwdLength() {
		return this.pwdLength;
	}

	public void setPwdLength(int pwdLength) {
		this.pwdLength = pwdLength;
	}

	public int getPwdExpires() {
		return this.pwdExpires;
	}

	public void setPwdExpires(int pwdExpires) {
		this.pwdExpires = pwdExpires;
	}

	public DigestEncoderTypes getDigestEncoderTypes() {
		return this.digestEncoderTypes;
	}

	public void setDigestEncoderTypes(DigestEncoderTypes digestEncoderTypes) {
		this.digestEncoderTypes = digestEncoderTypes;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PasswordParams [pwdLength=").append(this.pwdLength);
		builder.append(", pwdExpires=").append(this.pwdExpires);
		builder.append(", digestEncoderTypes=").append(this.digestEncoderTypes);
		builder.append("]");
		return builder.toString();
	}
}