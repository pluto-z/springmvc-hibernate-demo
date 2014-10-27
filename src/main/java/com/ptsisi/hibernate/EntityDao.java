package com.ptsisi.hibernate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaoding on 14-10-23.
 */
public interface EntityDao<T> {

	public T get(Serializable id);

	public List<T> getAll();

	public void save(T entity);

	public void saveOrUpdate(T entity);

	public void remove(List<T> entities);

	public void remove(Serializable id);

	public T findUniqueBy(String propertyName, Object value);
}
