package com.ptsisi.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by zhaoding on 14-10-29.
 */
public class AccountNotFoundException extends AuthenticationException {

  private static final long serialVersionUID = -3179257566150215502L;

  public AccountNotFoundException() {
    super();
  }

  public AccountNotFoundException(String message) {
    super(message);
  }

  public AccountNotFoundException(Throwable cause) {
    super(cause);
  }

  public AccountNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
