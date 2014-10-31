package com.ptsisi.security.utils;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import com.ptsisi.daily.User;
import com.ptsisi.daily.model.UserBean;
import com.ptsisi.security.DefaultRealm;

/**
 * Created by zhaoding on 14-10-29.
 */
public class PasswordUtil {

	private static final String HASH_ALGORITHM_NAME = "SHA-256";

	private static final int HASH_ITERATIONS = 1024;

	private static final SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();

	public static String genEncryptPassword(User user) {
		return getSimpleHash(user).toHex();
	}

	public static SimpleHash getSimpleHash(User user) {
		return new SimpleHash(HASH_ALGORITHM_NAME, user.getPassword(), user.getUsername() + user.getSalt(),
				HASH_ITERATIONS);
	}

	public static void populateEncryptInfo(User user) {
		user.setSalt(secureRandomNumberGenerator.nextBytes().toHex());
		user.setPassword(getSimpleHash(user).toHex());
	}

	public static void main(String[] args) {
		User user = new UserBean();
		user.setUsername("pluto4321@163.com");
		user.setPassword("1986020");
		populateEncryptInfo(user);
		System.out.println(user.getSalt());
		System.out.println(user.getPassword());
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-256");
		credentialsMatcher.setHashIterations(1024);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
				new DefaultRealm().getName());
		info.setCredentialsSalt(PasswordUtil.getSimpleHash(user).getSalt());
		AuthenticationToken token = new UsernamePasswordToken(user.getUsername(), "1986020");
		credentialsMatcher.doCredentialsMatch(token, info);
	}
}
