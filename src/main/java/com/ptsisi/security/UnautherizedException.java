package com.ptsisi.security;

import com.ptsisi.common.exception.ServiceException;

public class UnautherizedException extends ServiceException {

	private static final long serialVersionUID = -7923762644838767987L;

	public UnautherizedException() {
		super();
	}

	public UnautherizedException(String message) {
		super(message);
	}

	public UnautherizedException(Throwable cause) {
		super(cause);
	}

	public UnautherizedException(String message, Throwable cause) {
		super(message, cause);
	}
}
