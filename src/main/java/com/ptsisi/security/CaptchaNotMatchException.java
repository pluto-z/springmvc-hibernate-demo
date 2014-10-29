package com.ptsisi.security;

import org.apache.shiro.authc.AuthenticationException;

public class CaptchaNotMatchException extends AuthenticationException {

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
