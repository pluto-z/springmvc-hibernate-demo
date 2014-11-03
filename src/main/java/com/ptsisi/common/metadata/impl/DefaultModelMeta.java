package com.ptsisi.common.metadata.impl;

import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ptsisi.common.Entity;
import com.ptsisi.common.metadata.EntityContext;
import com.ptsisi.common.metadata.EntityType;
import com.ptsisi.common.metadata.ModelMeta;
import com.ptsisi.common.metadata.Type;

public class DefaultModelMeta implements ModelMeta {

  private static final Logger logger = LoggerFactory.getLogger(DefaultModelMeta.class);

  protected EntityContext context;

  @SuppressWarnings("unchecked")
  public <T extends Entity> T newInstance(final Class<T> clazz) {
    return (T) getEntityType(clazz).newInstance();
  }

  public <T extends Entity> T newInstance(final Class<T> clazz, final Serializable id) {
    EntityType type = getEntityType(clazz);
    @SuppressWarnings("unchecked")
    T entity = (T) type.newInstance();
    try {
      PropertyUtils.setProperty(entity, type.getIdName(), id);
    } catch (Exception e) {
      logger.error("initialize {} with id {} error", clazz, id);
    }
    return entity;
  }

  public EntityType getEntityType(String entityName) {
    return context.getEntityType(entityName);
  }

  public Type getType(String entityName) {
    return context.getType(entityName);
  }

  public String getEntityName(Object obj) {
    return context.getEntityName(obj);
  }

  public EntityType getEntityType(Class<?> clazz) {
    EntityType type = context.getEntityType(clazz);
    if (null == type) {
      type = new EntityType(clazz);
    }
    return type;
  }

  public void setContext(EntityContext context) {
    this.context = context;
  }

}
