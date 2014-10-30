package com.ptsisi.daily.web.service;

import java.io.File;
import java.util.List;

import com.ptsisi.daily.User;

/**
 * Created by zhaoding on 14-10-27.
 */
public interface UserService {

	User getUser(Integer id);

	User getUserByAccount(String username);

	List<User> getUsers();

	void saveOrUpdate(User user, File avatar);

	void saveOrUpdate(User user);

	void remove(User user);

	void remove(List<User> users);
}
