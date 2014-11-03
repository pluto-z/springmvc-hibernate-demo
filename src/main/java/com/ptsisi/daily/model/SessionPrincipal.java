package com.ptsisi.daily.model;

import com.ptsisi.daily.User;
import com.ptsisi.security.UnautherizedException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;

/**
 * Created by zhaoding on 14-10-31.
 */
public class SessionPrincipal implements Serializable {

	private User user;

	public SessionPrincipal(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static SessionPrincipal getCurrentSessionPrincipal() {
		Subject subject = SecurityUtils.getSubject();
		Object principal = subject.getPrincipal();
		if ((!subject.isAuthenticated() && !subject.isRemembered()) || principal == null) {
			throw new UnautherizedException("用户未经过认证");
		}
		return (SessionPrincipal) principal;
	}
}
