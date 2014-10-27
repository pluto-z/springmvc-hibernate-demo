package com.ptsisi.daily;

import java.util.List;
import java.util.Set;

/**
 * Created by zhaoding on 14-10-27.
 */
public interface Permission extends Entity {
	Permission getParent();

	void setParent(Permission parent);

	boolean isEnabled();

	void setEnabled(boolean enabled);

	String getName();

	void setName(String name);

	String getPermission();

	void setPermission(String permission);

	Set<Permission> getChildren();

	void setChildren(Set<Permission> children);

}
