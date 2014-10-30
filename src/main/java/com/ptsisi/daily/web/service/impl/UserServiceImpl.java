package com.ptsisi.daily.web.service.impl;

import com.google.common.collect.Lists;
import com.ptsisi.daily.Role;
import com.ptsisi.daily.User;
import com.ptsisi.daily.model.RoleBean;
import com.ptsisi.daily.web.service.UserService;
import com.ptsisi.hibernate.EntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaoding on 14-10-27.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	protected EntityDao entityDao;

	@Cacheable(value = "app")
	public User getUser(Integer id) {
		return entityDao.get(User.class, id);
	}

	@Cacheable(value = "app")
	public User getUserByAccount(String username) {
		return entityDao.findUniqueBy(User.class, "username", username);
	}

	@Cacheable(value = "app")
	public List<User> getUsers() {
		return entityDao.getAll(User.class);
	}

	@CachePut(value = "app")
	public void saveOrUpdate(User user, File avatar) {
		if (avatar != null && avatar.exists()) {
			try {
				user.setAvatar(entityDao.createBlob(new FileInputStream(avatar)));
			} catch (FileNotFoundException e) {
			}
		}
		saveOrUpdate(user);
	}

	@CachePut(value = "app")
	public void saveOrUpdate(User user) {
		List<Object> toSave = Lists.newArrayList();
		Date date = new Date();
		if (user.isTransient()) {
			Role role = entityDao.get(RoleBean.class, Role.USER);
			role.getUsers().add(user);
			user.setCreatedAt(date);
			toSave.add(role);
		}
		user.setEmail(user.getUsername());
		user.setUpdatedAt(date);
		toSave.add(user);
		entityDao.saveOrUpdate(toSave);
	}

	@CacheEvict(value = "app")
	public void remove(User user) {
		entityDao.remove(user);
	}

	@CacheEvict(value = "app")
	public void remove(List<User> users) {
		entityDao.remove(users);
	}
}
