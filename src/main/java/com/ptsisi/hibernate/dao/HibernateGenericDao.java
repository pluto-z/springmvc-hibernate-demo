package com.ptsisi.hibernate.dao;

import com.ptsisi.hibernate.EntityDao;
import com.ptsisi.hibernate.util.GenericUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaoding on 14-10-27.
 */
public abstract class HibernateGenericDao<T> extends HibernateDaoSupport<T> implements EntityDao<T> {

	protected Class<T> entityClass;

	public T get(Serializable id) {
		return get(entityClass, id);
	}

	public List<T> getAll() {
		return getAll(entityClass);
	}

	public void remove(Serializable id) {
		remove(entityClass, id);
	}

	public void save(T entity) {
		saveOrUpdate(entity);
	}

	protected HibernateGenericDao() {
		this.entityClass = GenericUtils.getSuperClassGenericType(getClass());
	}

	/**
	 * 查询全部，带排序
	 *
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	public List<T> getAllByOrder(String orderBy, boolean isAsc) {
		return getAll(entityClass, orderBy, isAsc);
	}

	/**
	 * 根据属性名和属性值查询对象
	 *
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> findBy(String propertyName, Object value) {
		return findBy(entityClass, propertyName, value);
	}

	/**
	 * 根据属性名和属性值进行查询对象，带排序
	 *
	 * @param propertyName
	 * @param value
	 * @param isAsc
	 * @param orderBy
	 * @return
	 */
	public List<T> findBy(String propertyName, Object value, boolean isAsc, String orderBy) {
		return findBy(entityClass, propertyName, value, orderBy, isAsc);
	}

	/**
	 * 根据属性名和属性值进行查询对象，返回符合条件的唯一对象。
	 * 如果对象不唯一将抛出异常
	 *
	 * @param <T>
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueBy(String propertyName, Object value) {
		return (T) findUniqueBy(entityClass, propertyName, value);
	}

	@Override public void saveOrUpdate(T entity) {
		super.saveOrUpdate(entity);
	}

	@Override public void remove(T entity) {
		super.remove(entity);
	}

	@Override public void remove(List<T> entities) {
		for (T entity : entities) {
			remove(entity);
		}
	}
}
