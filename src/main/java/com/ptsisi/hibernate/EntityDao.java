package com.ptsisi.hibernate;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate4.HibernateCallback;

/**
 * Created by zhaoding on 14-10-30.
 */
public interface EntityDao {
	<T> Object execute(HibernateCallback<T> action);

	<T> List<T> getAll(Class<T> entityClass);

	<T> List<T> getAll(Class<T> entityClass, String orderBy, boolean isAsc);

	<T> void saveOrUpdate(T entity);

	void evict(Object entity);

	Blob createBlob(InputStream inputStream, int length);

	Blob createBlob(InputStream inputStream);

	Clob createClob(String str);

	void refresh(Object entity);

	<T> void remove(T entity);

	<T> void remove(Class<T> entityClass, Serializable id);

	<T> T get(Class<T> entityClass, Serializable id);

	Query createQuery(String hql, Object... values);

	<T> Criteria createCriteria(Class<T> entityClass, Criterion... criterions);

	<T> Criteria createCriteria(Class<T> entityClass, String orderBy,
			boolean isAsc, Criterion... criterions);

	<T> List<T> find(String hql, Object... values);

	<T> List<T> findBy(Class<T> entityClass, String propertyName, Object value);

	<T> List<T> findBy(Class<T> entityClass, String propertyName, Object value,
			String orderBy, boolean isAsc);

	<T> T findUniqueBy(Class<T> entityClass, String propertyName, Object value);

	<T> SinglePage<T> pagedQuery(String hql, int pageNo, int pageSize,
			Object... values);

	<T> SinglePage<T> pagedQuery(Criteria criteria, int pageNo, int pageSize);

	<T> SinglePage<T> pagedQuery(Class<T> entityClass, int pageNo,
			int pageSize, Criterion... criterions);

	<T> SinglePage<T> pagedQuery(Class<T> entityClass, int pageNo,
			int pageSize, String orderBy, boolean isAsc,
			Criterion... criterions);

	<T> void saveOrUpdate(final List<T> entities);
}
