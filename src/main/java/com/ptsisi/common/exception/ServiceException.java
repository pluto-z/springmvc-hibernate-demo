package com.ptsisi.common.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -1581267521101113457L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
