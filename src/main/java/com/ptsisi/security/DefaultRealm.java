package com.ptsisi.security;

import com.ptsisi.daily.User;
import com.ptsisi.daily.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

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
			return new SimpleAuthenticationInfo(user.getUsername(), user
					.getPassword(), user.getFullname());
		} else {
			return null;
		}
	}
}
