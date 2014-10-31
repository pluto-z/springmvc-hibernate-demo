package com.ptsisi.daily;

import com.ptsisi.daily.model.MenuBean;

import java.util.Set;

/**
 * Created by zhaoding on 14-10-31.
 */
public interface Menu extends Comparable<Menu>, com.ptsisi.common.HierarchyEntity<Menu> {

	String getName();

	void setName(String name);

	String getEntry();

	void setEntry(String entry);

	Set<Resource> getResources();

	void setResources(Set<Resource> resources);

	boolean isEnabled();

	void setEnabled(boolean enabled);

	String getIcon();

	void setIcon(String icon);
}
