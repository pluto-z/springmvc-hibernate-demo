package com.ptsisi.hibernate.dao;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.jdbc.StreamUtils;
import org.hibernate.internal.CriteriaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ptsisi.common.model.IntegerIdTimeObject;
import com.ptsisi.hibernate.HibernateDaoHandler;
import com.ptsisi.hibernate.SinglePage;

/**
 * Created by zhaoding on 14-10-23.
 */
@Repository
public class HibernateDaoSupport implements HibernateDaoHandler,
		com.ptsisi.hibernate.EntityDao {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public <T> T execute(HibernateCallback<T> action) {
		return action.doInHibernate(getSession());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> List<T> getAll(final Class<T> entityClass) {
		return (List<T>) execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Criteria criteria = session.createCriteria(entityClass);
				return criteria.list();
			}
		});
	}

	/**
	 * 获取全部对象，带排序功能
	 * 
	 * @param <T>
	 * @param entityClass
	 *            实体对象
	 * @param orderBy
	 *            　排序字段
	 * @param isAsc
	 *            升序或降序
	 * @return
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> getAll(final Class<T> entityClass, final String orderBy,
			final boolean isAsc) {
		Assert.hasText(orderBy);
		return (List<T>) execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				if (isAsc)
					return session.createCriteria(entityClass)
							.addOrder(Order.asc(orderBy)).list();
				return session.createCriteria(entityClass)
						.addOrder(Order.desc(orderBy)).list();
			}

		});

	}

	/**
	 * 保存对象
	 * 
	 * @param entity
	 */
	@Override
	public <T> void saveOrUpdate(final T entity) {
		execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) {
				if (entity instanceof IntegerIdTimeObject) {
					Date date = new Date();
					if (((IntegerIdTimeObject) entity).isTransient()) {
						((IntegerIdTimeObject) entity).setCreatedAt(date);
					}
					((IntegerIdTimeObject) entity).setUpdatedAt(date);
				}
				session.saveOrUpdate(entity);
				return null;
			}
		});
	}

	@Override
	public <T> void saveOrUpdate(final List<T> entities) {
		execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) {
				for (T entity : entities) {
					if (entity instanceof IntegerIdTimeObject) {
						Date date = new Date();
						if (((IntegerIdTimeObject) entity).isTransient()) {
							((IntegerIdTimeObject) entity).setCreatedAt(date);
						}
						((IntegerIdTimeObject) entity).setUpdatedAt(date);
					}
					session.saveOrUpdate(entity);
				}
				return null;
			}
		});
	}

	@Override
	public void evict(final Object entity) {
		getSession().evict(entity);
	}

	@Override
	public Blob createBlob(final InputStream inputStream, final int length) {
		return (Blob) execute(new HibernateCallback<Blob>() {
			public Blob doInHibernate(Session session) {
				return Hibernate.getLobCreator(session).createBlob(inputStream,
						length);
			}
		});
	}

	@Override
	public Blob createBlob(InputStream inputStream) {
		try {
			final ByteArrayOutputStream buffer = new ByteArrayOutputStream(
					inputStream.available());
			StreamUtils.copy(inputStream, buffer);
			return (Blob) execute(new HibernateCallback<Blob>() {
				public Blob doInHibernate(Session session) {
					return Hibernate.getLobCreator(session).createBlob(
							buffer.toByteArray());
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Clob createClob(final String str) {
		return (Clob) execute(new HibernateCallback<Clob>() {
			public Clob doInHibernate(Session session) {
				return Hibernate.getLobCreator(session).createClob(str);
			}
		});
	}

	@Override
	public void refresh(Object entity) {
		getSession().refresh(entity);
	}

	public <T> void remove(final Collection<T> entities) {
		execute(new HibernateCallback<Collection<T>>() {
			public Collection<T> doInHibernate(Session session) {
				for (T entity : entities) {
					session.delete(entity);
				}
				return null;
			}
		});
	}

	/**
	 * 删除对象
	 * 
	 * @param entity
	 */
	@Override
	public <T> void remove(final T entity) {
		execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) {
				session.delete(entity);
				return null;
			}

		});
	}

	/**
	 * @param entityClass
	 * @param id
	 */
	@Override
	public <T> void remove(Class<T> entityClass, Serializable id) {
		remove(get(entityClass, id));
	}

	/**
	 * 根据Id获取对象。
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param id
	 *            实体Id
	 * @return 实体对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(final Class<T> entityClass, final Serializable id) {
		if (entityClass.isInterface()) {
			throw new RuntimeException("interface is not support here");
		} else {
			return (T) execute(new HibernateCallback<T>() {
				public T doInHibernate(Session session) {
					return (T) session.get(entityClass, id);
				}
			});
		}
	}

	/**
	 * 创建一个Query对象。
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	@Override
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 创建Criteria对象。
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	@Override
	public <T> Criteria createCriteria(Class<T> entityClass,
			Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 创建Criteria对象，有排序功能。
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param orderBy
	 * @param isAsc
	 * @param criterions
	 * @return
	 */
	@Override
	public <T> Criteria createCriteria(Class<T> entityClass, String orderBy,
			boolean isAsc, Criterion... criterions) {
		Assert.hasText(orderBy);
		final Class<?> clazz;
		if (entityClass.isInterface()) {
			clazz = entityClass.getClass();
		} else {
			clazz = entityClass;
		}
		Criteria criteria = createCriteria(clazz, criterions);
		if (isAsc) {
			criteria.addOrder(Order.asc(orderBy));
		} else {
			criteria.addOrder(Order.desc(orderBy));
		}
		return criteria;
	}

	/**
	 * 根据hql查询
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<?> find(final String hql, final Object... values) {
		Assert.hasText(hql);
		return createQuery(hql, values).list();
	}

	/**
	 * 根据属性名和属性值查询.
	 * 
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> findBy(Class<T> entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, Restrictions.eq(propertyName, value))
				.list();
	}

	/**
	 * 根据属性名和属性值查询. 有排序
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	@Override
	@SuppressWarnings({ "unchecked" })
	public <T> List<T> findBy(Class<T> entityClass, String propertyName,
			Object value, String orderBy, boolean isAsc) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return createCriteria(entityClass, orderBy, isAsc,
				Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据属性名和属性值 查询 且要求对象唯一.
	 * 
	 * @return 符合条件的唯一对象.
	 */
	@Override
	@SuppressWarnings({ "unchecked" })
	public <T> T findUniqueBy(Class<T> entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/**
	 * 分页 通过hql进行
	 * 
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @param values
	 * @return
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> SinglePage<T> pagedQuery(String hql, int pageNo, int pageSize,
			Object... values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1);
		String countQueryString = "select count(*)"
				+ removeSelect(removeOrders(hql));
		List countList = find(countQueryString, values);
		long totalCount = (Long) countList.get(0);
		if (totalCount < 1) {
			return new SinglePage<T>();
		}
		Query query = createQuery(hql, values);
		List<T> list = query.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();
		return new SinglePage<T>(pageNo, pageSize, totalCount, list);
	}

	/**
	 * 分页 通过criteria
	 * 
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	@SuppressWarnings({ "unchecked" })
	public <T> SinglePage<T> pagedQuery(Criteria criteria, int pageNo,
			int pageSize) {
		Assert.notNull(criteria);
		Assert.isTrue(pageNo >= 1);
		CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;

		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = criteriaImpl.getProjection();
		// 取得总的数据数
		long totalCount = (Long) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		// 将之前的Projection和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (totalCount < 1)
			return new SinglePage<T>();
		List<T> data = criteria.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();
		return new SinglePage<T>(pageNo, pageSize, totalCount, data);
	}

	/**
	 * 分页查询函数
	 * 
	 * @param entityClass
	 * @param pageNo
	 * @param pageSize
	 * @param criterions
	 * @return
	 */
	@Override
	public <T> SinglePage<T> pagedQuery(Class<T> entityClass, int pageNo,
			int pageSize, Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, criterions);
		return pagedQuery(criteria, pageNo, pageSize);
	}

	/**
	 * 分页查询带排序
	 * 
	 * @param entityClass
	 * @param pageNo
	 * @param pageSize
	 * @param orderBy
	 * @param isAsc
	 * @param criterions
	 * @return
	 */
	@Override
	public <T> SinglePage<T> pagedQuery(Class<T> entityClass, int pageNo,
			int pageSize, String orderBy, boolean isAsc,
			Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, orderBy, isAsc,
				criterions);
		return pagedQuery(criteria, pageNo, pageSize);
	}

	/**
	 * 去除hql的select子句。
	 * 
	 * @param hql
	 * @return
	 * @see #pagedQuery(String, int, int, Object[])
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, hql);
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderBy子句。
	 * 
	 * @param hql
	 * @return
	 * @see #pagedQuery(String, int, int, Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

}
