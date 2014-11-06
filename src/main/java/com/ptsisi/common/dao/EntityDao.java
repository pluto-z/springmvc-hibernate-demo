package com.ptsisi.common.dao;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ptsisi.common.Entity;
import com.ptsisi.common.collection.page.PageLimit;
import com.ptsisi.common.query.Query;
import com.ptsisi.common.query.QueryBuilder;

public interface EntityDao {

  <T extends Entity> T get(Class<T> clazz, Serializable id);

  <T> T get(String entityName, Serializable id);

  <T extends Entity> List<T> getAll(Class<T> clazz);

  <T extends Entity> List<T> get(Class<T> entityClass, Serializable[] values);

  <T extends Entity> List<T> get(Class<T> entityClass, Collection<Serializable> values);

  <T extends Entity> List<T> get(Class<T> clazz, String keyName, Object... values);

  <T extends Entity> List<T> get(Class<T> clazz, String keyName, Collection<?> values);

  <T extends Entity> List<T> get(Class<T> clazz, String[] attrs, Object... values);

  <T extends Entity> List<T> get(Class<T> clazz, Map<String, Object> parameterMap);

  <T> List<T> get(String entityName, String keyName, Object... values);

  <T> Object search(Query<T> query);

  <T> Object searchObj(QueryBuilder<T> builder);

  <T> List<T> search(QueryBuilder<T> builder);

  <T> T uniqueResult(QueryBuilder<T> builder);

  <T> List<T> search(final String query, final Object... params);

  <T> List<T> search(final String query, final Map<String, Object> params);

  <T> List<T> search(String query, final Map<String, Object> params, PageLimit limit, boolean cacheable);

  int executeUpdate(String query, Object... arguments);

  int[] executeUpdateRepeatly(String query, Collection<Object[]> arguments);

  int executeUpdate(String query, Map<String, Object> parameterMap);

  void saveOrUpdate(Object... entities);

  void save(Object... entities);

  void saveOrUpdate(Collection<?> entities);

  void saveOrUpdate(String entityName, Object... entities);

  void saveOrUpdate(String entityName, Collection<?> entities);

  int update(Class<?> entityClass, String attr, Object[] values, Map<String, Object> updateParams);

  int update(Class<?> entityClass, String attr, Object[] values, String[] argumentName, Object[] argumentValue);

  void remove(Object... entities);

  void remove(Collection<?> entities);

  boolean remove(Class<?> entityClass, String attr, Object... values);

  boolean remove(Class<?> entityClass, String attr, Collection<?> values);

  boolean remove(Class<?> entityClass, Map<String, Object> parameterMap);

  Blob createBlob(InputStream inputStream, int length);

  Blob createBlob(InputStream inputStream);

  Clob createClob(String str);

  void evict(Object entity);

  <T> T initialize(T entity);

  void refresh(Object entity);

  long count(String entityName, String keyName, Object value);

  long count(Class<?> entityClass, String keyName, Object value);

  long count(Class<?> entityClass, String[] attrs, Object[] values, String countAttr);

  boolean exist(Class<?> entityClass, String attr, Object value);

  boolean exist(String entityName, String attr, Object value);

  boolean exist(Class<?> entity, String[] attrs, Object[] values);

  boolean duplicate(String entityName, Serializable id, Map<String, Object> params);

  boolean duplicate(Class<? extends Entity> clazz, Serializable id, String codeName, Object codeValue);

  void execute(Operation... opts);

  void execute(Operation.Builder builder);
}
