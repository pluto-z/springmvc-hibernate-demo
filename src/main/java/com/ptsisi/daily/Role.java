package com.ptsisi.daily;

import java.util.Set;

/**
 * Created by zhaoding on 14-10-27.
 */
public interface Role extends Entity, TimeEntity {

	String getName();

	void setName(String name);

	boolean isEnabled();

	void setEnabled(boolean enabled);

	Set<User> getUsers();

	void setUsers(Set<User> users);


}
