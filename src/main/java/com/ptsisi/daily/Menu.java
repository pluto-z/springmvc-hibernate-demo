package com.ptsisi.daily;

import java.util.List;

import com.ptsisi.common.Entity;
import com.ptsisi.common.TimeEntity;
import com.ptsisi.daily.model.MenuBean;

/**
 * Created by zhaoding on 14-10-28.
 */
public interface Menu extends Entity, TimeEntity {

	String getName();

	void setName(String name);

	int getIndexNo();

	void setIndexNo(int indexNo);

	MenuBean getParent();

	void setParent(MenuBean parent);

	List<MenuBean> getChildren();

	void setChildren(List<MenuBean> children);

	Resource getResource();

	void setResource(Resource resource);

	boolean isEnabled();

	void setEnabled(boolean enabled);
}
