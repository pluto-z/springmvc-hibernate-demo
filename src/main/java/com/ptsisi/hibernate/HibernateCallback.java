package com.ptsisi.hibernate;

import org.hibernate.Session;

/**
 * Created by zhaoding on 14-10-23.
 */
public interface HibernateCallback {

	Object doInHibernate(Session session);
}
