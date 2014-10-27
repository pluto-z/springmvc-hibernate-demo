package com.ptsisi.daily.service.impl;

import com.ptsisi.daily.User;
import com.ptsisi.daily.dao.UserManager;
import com.ptsisi.daily.dao.impl.UserManagerImpl;
import com.ptsisi.daily.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhaoding on 14-10-27.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	protected UserManager userManager;

	@Cacheable(value = "app")
	public User getUser(Integer id) {
		return userManager.get(id);
	}

	@Cacheable(value = "app")
	public User getUserByAccount(String username) {
		return userManager.findUniqueBy("username", username);
	}

	@Cacheable(value = "app")
	public List<User> getUsers() {
		return userManager.getAll();
	}

	@CachePut(value = "app")
	public void saveOrUpdate(User user) {
		userManager.saveOrUpdate(user);
	}

	@CacheEvict(value = "app")
	public void remove(User user) {
		userManager.remove(user);
	}

	@CacheEvict(value = "app")
	public void remove(List<User> users) {
		userManager.remove(users);
	}
}
