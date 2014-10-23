package com.ptsisi.hibernate.dao;

import com.ptsisi.hibernate.EntityDao;
import com.ptsisi.hibernate.HibernateDaoHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhaoding on 14-10-23.
 */
public abstract class HibernateDaoSupport<T> implements HibernateDaoHandler {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	@Override public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
