package com.ptsisi.daily;

import com.ptsisi.common.Entity;
import com.ptsisi.common.TimeEntity;

import java.sql.Blob;
import java.util.Set;

/**
 * Created by zhaoding on 14-10-29.
 */
public interface User extends Entity, TimeEntity {

  String getUsername();

  void setUsername(String username);

  String getSalt();

  void setSalt(String salt);

  String getPassword();

  void setPassword(String password);

  String getFullName();

  void setFullName(String fullName);

  String getEmail();

  void setEmail(String email);

  Blob getPortrait();

  void setPortrait(Blob portrait);

  boolean isEnabled();

  void setEnabled(boolean enabled);

  Set<Role> getRoles();

  void setRoles(Set<Role> roles);

  Set<Resource> getResources();
}
