
package com.parking.common.exception;

public class GenericRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -7416640820340409247L;
	protected Integer errorCode;
	protected String errorMessage;
	protected String action;

	public GenericRuntimeException() {
	}

	public GenericRuntimeException(Throwable t) {
		super(t);
	}

	public GenericRuntimeException(String message, Throwable t) {
		super(message, t);
	}

	public GenericRuntimeException(String message) {
		super(message);
	}

	public GenericRuntimeException(String message, Integer errorCode, Throwable t) {
		super(t);
		this.errorCode = errorCode;
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}