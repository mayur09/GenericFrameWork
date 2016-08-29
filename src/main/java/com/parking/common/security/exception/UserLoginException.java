package com.parking.common.security.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class UserLoginException extends BadCredentialsException {
	private static final long serialVersionUID = 1L;
	private String message;
	private int errorCode;

	public UserLoginException(String message) {
		super(message);
		this.message = message;
	}

	public UserLoginException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}