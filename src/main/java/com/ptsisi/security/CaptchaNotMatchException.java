package com.ptsisi.security;

import org.apache.shiro.authc.AuthenticationException;

public class CaptchaNotMatchException extends AuthenticationException {

	private static final long serialVersionUID = -4723801407555012364L;

	public CaptchaNotMatchException() {
		super();
	}

	public CaptchaNotMatchException(String message) {
		super(message);
	}

	public CaptchaNotMatchException(Throwable cause) {
		super(cause);
	}

	public CaptchaNotMatchException(String message, Throwable cause) {
		super(message, cause);
	}
}
