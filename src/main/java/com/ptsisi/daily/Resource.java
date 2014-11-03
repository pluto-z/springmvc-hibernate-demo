package com.ptsisi.daily;

import com.ptsisi.common.Entity;

/**
 * Created by zhaoding on 14-10-28.
 */
public interface Resource extends Entity {

  String getName();

  void setName(String name);

  String getPermission();

  void setPermission(String permission);

  String getValue();

  void setValue(String value);
}
