package com.ptsisi.common;

import java.util.List;

/**
 * Created by zhaoding on 14-10-31.
 */
public interface HierarchyEntity<T> extends Entity {

	int getIndexNo();

	void setIndexNo(int indexNo);

	T getParent();

	void setParent(T parent);

	List<T> getChildren();

	void setChildren(List<T> children);

	String getIndex();

}
