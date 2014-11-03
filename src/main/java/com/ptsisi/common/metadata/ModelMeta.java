package com.ptsisi.common.metadata;

import java.io.Serializable;

import com.ptsisi.common.Entity;

public interface ModelMeta {

  <T extends Entity> T newInstance(final Class<T> clazz);

  <T extends Entity> T newInstance(final Class<T> clazz, final Serializable id);

  EntityType getEntityType(String entityName);

  Type getType(String name);

  String getEntityName(Object obj);

  EntityType getEntityType(Class<?> clazz);

}
