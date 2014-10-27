package com.ptsisi.daily.service;

import com.ptsisi.daily.User;

import java.util.List;

/**
 * Created by zhaoding on 14-10-27.
 */
public interface UserService {

	User getUser(Integer id);

	User getUserByAccount(String username);

	List<User> getUsers();

	void saveOrUpdate(User user);

	void remove(User user);

	void remove(List<User> users);
}
