package com.ptsisi.daily.web.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.ptsisi.common.BaseServiceImpl;
import com.ptsisi.common.query.builder.OqlBuilder;
import com.ptsisi.daily.Role;
import com.ptsisi.daily.User;
import com.ptsisi.daily.model.RoleBean;
import com.ptsisi.daily.model.UserBean;
import com.ptsisi.daily.web.service.UserService;

/**
 * Created by zhaoding on 14-10-27.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

  @Cacheable(value = "app")
  public User getUser(Integer id) {
    return entityDao.get(UserBean.class, id);
  }

  @Cacheable(value = "app")
  public User getUserByAccount(String username) {
    return entityDao.uniqueResult(OqlBuilder.from(User.class, "user").where("user.username = :username", username));
  }

  @Cacheable(value = "app")
  public List<User> getUsers() {
    return entityDao.getAll(User.class);
  }

  public byte[] getPortrait(User user) {
    byte[] b = null;
    Blob portrait = user.getPortrait();
    try {
      if (portrait != null) {
        long in = 0;
        b = user.getPortrait().getBytes(in, (int) (portrait.length()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return b;
  }

  @CachePut(value = "app")
  public void saveOrUpdate(User user, File avatar) {
    if (avatar != null && avatar.exists()) {
      try {
        user.setPortrait(entityDao.createBlob(new FileInputStream(avatar)));
      } catch (FileNotFoundException e) {
      }
    }
    saveOrUpdate(user);
  }

  @CachePut(value = "app")
  public void saveOrUpdate(User user) {
    List<Object> toSave = Lists.newArrayList();
    if (user.isTransient()) {
      Role role = entityDao.get(RoleBean.class, Role.USER);
      role.getUsers().add(user);
      toSave.add(role);
    }
    toSave.add(user);
    entityDao.saveOrUpdate(toSave);
  }

  @CacheEvict(value = "app")
  public void remove(User user) {
    entityDao.remove(user);
  }

  @CacheEvict(value = "app")
  public void remove(List<User> users) {
    entityDao.remove(users);
  }
}
