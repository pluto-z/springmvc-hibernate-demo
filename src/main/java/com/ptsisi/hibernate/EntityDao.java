package com.ptsisi.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate4.HibernateCallback;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.List;

/**
 * Created by zhaoding on 14-10-30.
 */
public interface EntityDao {
	Object execute(HibernateCallback action);

	<T> List<T> getAll(Class<T> entityClass);

	@SuppressWarnings({ "unchecked", "rawtypes" }) <T> List<T> getAll(Class<T> entityClass, String orderBy,
			boolean isAsc);

	@SuppressWarnings({ "unchecked", "rawtypes" }) <T> void saveOrUpdate(T entity);

	void evict(Object entity);

	@SuppressWarnings({ "unchecked", "rawtypes" }) Blob createBlob(InputStream inputStream, int length);

	@SuppressWarnings({ "unchecked", "rawtypes" }) Blob createBlob(InputStream inputStream);

	@SuppressWarnings({ "unchecked", "rawtypes" }) Clob createClob(String str);

	void refresh(Object entity);

	@SuppressWarnings({ "unchecked", "rawtypes" }) <T> void remove(T entity);

	<T> void remove(Class<T> entityClass, Serializable id);

	@SuppressWarnings({ "hiding", "unchecked", "rawtypes" }) <T> T get(Class<T> entityClass, Serializable id);

	Query createQuery(String hql, Object... values);

	<T> Criteria createCriteria(Class<T> entityClass,
			Criterion... criterions);

	<T> Criteria createCriteria(Class<T> entityClass, String orderBy,
			boolean isAsc, Criterion... criterions);

	@SuppressWarnings("rawtypes") <T> List find(String hql, Object... values);

	@SuppressWarnings({ "unchecked" }) <T> List<T> findBy(Class<T> entityClass, String propertyName,
			Object value);

	@SuppressWarnings({ "unchecked" }) <T> List<T> findBy(Class<T> entityClass, String propertyName,
			Object value, String orderBy, boolean isAsc);

	@SuppressWarnings({ "unchecked" }) <T> T findUniqueBy(Class<T> entityClass, String propertyName,
			Object value);

	@SuppressWarnings({ "unchecked", "rawtypes" }) <T> SinglePage<T> pagedQuery(String hql, int pageNo, int pageSize,
			Object... values);

	@SuppressWarnings({ "unchecked" }) <T> SinglePage<T> pagedQuery(Criteria criteria, int pageNo, int pageSize);

	<T> SinglePage<T> pagedQuery(Class<T> entityClass, int pageNo,
			int pageSize, Criterion... criterions);

	<T> SinglePage<T> pagedQuery(Class<T> entityClass, int pageNo,
			int pageSize, String orderBy, boolean isAsc,
			Criterion... criterions);

	<T> void saveOrUpdate(final List<T> entities);
}
