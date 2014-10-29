package com.ptsisi.daily;

import com.ptsisi.common.Entity;

import java.util.Set;

/**
 * Created by zhaoding on 14-10-28.
 */
public interface Role extends Entity {

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	Set<User> getUsers();

	void setUsers(Set<User> users);

	Set<Resource> getResources();

	void setResources(Set<Resource> resources);
}
