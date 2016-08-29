/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.exception;

public class GenericDAOException extends GenericRuntimeException {
	private static final long serialVersionUID = -4589935155476571905L;

	public GenericDAOException() {
	}

	public GenericDAOException(Throwable t) {
		super(t);
	}

	public GenericDAOException(String message) {
		super(message);
	}

	public GenericDAOException(int errorCode) {
		this.errorCode = Integer.valueOf(errorCode);
	}

	public GenericDAOException(String message, Throwable t) {
		super(message, t);
	}

	public GenericDAOException(String message, Integer code, Throwable t) {
		super(message, code, t);
	}
}