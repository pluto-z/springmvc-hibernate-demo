package com.ptsisi.daily.web.service;

import com.ptsisi.common.BaseService;
import com.ptsisi.common.query.builder.OqlBuilder;
import com.ptsisi.daily.model.CustomPrincipal;
import com.ptsisi.daily.model.User;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaoding on 14-10-27.
 */
@Service
@CacheConfig(cacheNames = "users")
public class UserService extends BaseService {

	public User getLoginUser() {
		CustomPrincipal customPrincipal = CustomPrincipal.getCurrentPrincipal();
		return getUser(customPrincipal.getUser().getId());
	}

	@Cacheable(key = "#id")
	public User getUser(Integer id) {
		return entityDao.get(User.class, id);
	}

	@Cacheable(key = "#username")
	public User getUserByAccount(String username) {
		System.out.println(111);
		return entityDao.uniqueResult(OqlBuilder.from(User.class, "user").where("user.username = :username", username));
	}

	public byte[] getPortrait(User user) {
		byte[] b = null;
		Blob portrait = user.getPortrait();
		try {
			if (portrait != null) {
				long in = 0;
				b = user.getPortrait().getBytes(in, (int) (portrait.length()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	@Caching(put = { @CachePut(key = "#user.id"), @CachePut(key = "#user.username") })
	public User saveOrUpdate(User user, InputStream avatar) {
		if (avatar != null) {
			user.setPortrait(entityDao.createBlob(avatar));
		}
		Date date = new Date();
		if (user.isTransient()) {
			user.setCreatedAt(date);
		}
		user.setUpdatedAt(date);
		entityDao.saveOrUpdate(user);
		return user;
	}

	@Caching(put = { @CachePut(key = "#user.id"), @CachePut(key = "#user.username") })
	public User saveOrUpdate(User user, File avatar) {
		if (avatar != null && avatar.exists()) {
			try {
				user.setPortrait(entityDao.createBlob(new FileInputStream(avatar)));
			} catch (FileNotFoundException e) {
			}
		}
		Date date = new Date();
		if (user.isTransient()) {
			user.setCreatedAt(date);
		}
		user.setUpdatedAt(date);
		entityDao.saveOrUpdate(user);
		return user;
	}

	@Caching(put = { @CachePut(key = "#user.id"), @CachePut(key = "#user.username") })
	public User saveOrUpdate(User user) {
		Date date = new Date();
		if (user.isTransient()) {
			user.setCreatedAt(date);
		}
		user.setUpdatedAt(date);
		entityDao.saveOrUpdate(user);
		return user;
	}

	@Caching(evict = { @CacheEvict(key = "#user.id"), @CacheEvict(key = "#user.username") })
	public void remove(User user) {
		entityDao.remove(user);
	}

	@CacheEvict(allEntries = true, beforeInvocation = true)
	public void remove(List<User> users) {
		entityDao.remove(users);
	}

	@CacheEvict(allEntries = true)
	public void reload() {
	}
}
