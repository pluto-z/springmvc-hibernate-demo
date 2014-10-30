package com.ptsisi.daily;

import java.util.Set;

import com.ptsisi.common.Entity;

/**
 * Created by zhaoding on 14-10-28.
 */
public interface Role extends Entity {

	public static final Integer ADMIN = 1;
	public static final Integer USER = 2;

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	Set<User> getUsers();

	void setUsers(Set<User> users);

	Set<Resource> getResources();

	void setResources(Set<Resource> resources);
}
