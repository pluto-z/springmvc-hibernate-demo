package com.ptsisi.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.ptsisi.daily.User;
import com.ptsisi.daily.web.service.UserService;
import com.ptsisi.security.utils.PasswordUtil;

/**
 * Created by zhaoding on 14-10-27.
 */
public class DefaultRealm extends AuthorizingRealm {

	@Autowired
	protected UserService userService;

	@Override protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		User user = userService.getUserByAccount(usernamePasswordToken.getUsername());
		if (user != null) {
			if (!user.isEnabled()) {
				throw new DisabledAccountException(user.getUsername() + " has been disabled");
			}
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
					getName());
			info.setCredentialsSalt(PasswordUtil.getSimpleHash(user).getSalt());
			return info;
		} else {
			throw new AccountNotFoundException("username [" + usernamePasswordToken.getUsername() + "] doesn't exist]");
		}
	}
}
