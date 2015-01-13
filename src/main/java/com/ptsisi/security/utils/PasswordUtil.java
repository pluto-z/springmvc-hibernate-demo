package com.ptsisi.security.utils;

import com.ptsisi.daily.model.User;
import com.ptsisi.security.DefaultRealm;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

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
		User user = new User();
		user.setUsername("zhangchengsi");
		user.setPassword("19870720");
		populateEncryptInfo(user);
		System.out.println(user.getSalt());
		System.out.println(user.getPassword());
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-256");
		credentialsMatcher.setHashIterations(1024);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
				PasswordUtil.getSimpleHash(user).getSalt(), new DefaultRealm().getName());
		AuthenticationToken token = new UsernamePasswordToken(user.getUsername(), "19870720");
		System.out.println(credentialsMatcher.doCredentialsMatch(token, info));
	}
}
