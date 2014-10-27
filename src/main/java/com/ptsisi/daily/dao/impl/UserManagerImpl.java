package com.ptsisi.daily.dao.impl;

import org.springframework.stereotype.Repository;

import com.ptsisi.daily.User;
import com.ptsisi.daily.dao.UserManager;
import com.ptsisi.hibernate.dao.HibernateGenericDao;

/**
 * Created by zhaoding on 14-10-27.
 */
@Repository
public class UserManagerImpl extends HibernateGenericDao<User> implements UserManager {

}
