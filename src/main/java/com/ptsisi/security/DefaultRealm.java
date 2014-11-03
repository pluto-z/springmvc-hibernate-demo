package com.ptsisi.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ptsisi.common.exception.ServiceException;
import com.ptsisi.daily.Resource;
import com.ptsisi.daily.Role;
import com.ptsisi.daily.User;
import com.ptsisi.daily.model.SessionPrincipal;
import com.ptsisi.daily.web.service.SecurityService;
import com.ptsisi.daily.web.service.UserService;
import com.ptsisi.security.utils.PasswordUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * Created by zhaoding on 14-10-27.
 */
public class DefaultRealm extends AuthorizingRealm {

	@Autowired
	protected UserService userService;

	@Autowired
	protected SecurityService securityService;

	protected List<String> defaultPermissions = Lists.newArrayList();

	/**
	 * 设置默认的 permission
	 *
	 * @param defaultPermissionString permission 如果存在多个值，使用逗号","分割
	 */
	public void setDefaultPermissionString(String defaultPermissionString) {
		String[] perms = StringUtils.split(defaultPermissionString, ",");
		CollectionUtils.addAll(defaultPermissions, perms);
	}

	public void setDefaultPermission(List<String> defaultPermissions) {
		this.defaultPermissions = defaultPermissions;
	}

	@Override protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SessionPrincipal sessionPrincipal = principals.oneByType(SessionPrincipal.class);
		if (sessionPrincipal == null) {
			throw new ServiceException("session 变量对象不存在");
		}
		User currentUser = sessionPrincipal.getUser();
		currentUser = userService.getUser(currentUser.getId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//加载用户资源信息
		Set<Resource> resources = securityService.getResources(currentUser);
		//添加用户拥有的permission
		addPermissions(info, resources);
		//添加用户拥有的role
		addRoles(info, currentUser);
		return info;
	}

	private void addRoles(SimpleAuthorizationInfo info, User user) {
		Set<String> roleNames = Sets.newHashSet();
		for (Role role : user.getRoles()) {
			roleNames.add(role.getName());
		}
		info.addRoles(roleNames);
	}

	private void addPermissions(SimpleAuthorizationInfo info, Set<Resource> authorizationInfo) {
		List<String> permissions = Lists.newArrayList();
		//添加默认的permissions到permissions
		for (String permission : defaultPermissions) {
			permissions.add(permission);
		}
		//解析当前用户资源中的permissions
		for (Resource resource : authorizationInfo) {
			String permission = resource.getPermission();
			if (StringUtils.isNotBlank(permission)) {
				permissions.add(StringUtils.substringBetween(permission.toString(), "perms[", "]"));
			}
		}
		info.addStringPermissions(permissions);
	}

	@Override protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		User user = userService.getUserByAccount(usernamePasswordToken.getUsername());
		if (user != null) {
			if (!user.isEnabled()) {
				throw new DisabledAccountException(user.getUsername() + " has been disabled");
			}
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(new SessionPrincipal(user), user.getPassword(),
					getName());
			info.setCredentialsSalt(PasswordUtil.getSimpleHash(user).getSalt());
			return info;
		} else {
			throw new AccountNotFoundException("username [" + usernamePasswordToken.getUsername() + "] doesn't exist]");
		}
	}
}
