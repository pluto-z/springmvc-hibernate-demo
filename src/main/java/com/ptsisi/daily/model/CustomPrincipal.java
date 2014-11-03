package com.ptsisi.daily.model;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.ptsisi.daily.User;
import com.ptsisi.security.UnautherizedException;

public class CustomPrincipal {

  private User user;

  public CustomPrincipal(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public static CustomPrincipal getCurrentPrincipal() {
    Subject subject = SecurityUtils.getSubject();
    Object principal = subject.getPrincipal();
    if ((!subject.isAuthenticated() && !subject.isRemembered()) || principal == null) { throw new UnautherizedException(
        "用户未经过认证"); }
    return (CustomPrincipal) principal;
  }

}
