package com.ptsisi.common.metadata;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.ptsisi.common.Entity;

public class EntityType extends AbstractType {

  private String entityName;

  private Class<?> entityClass;

  private Map<String, Type> propertyTypes = Maps.newHashMap();

  private String idName;

  public EntityType(String entityName, Class<?> entityClass, String idName) {
    super();
    Assert.notNull(idName);
    Assert.notNull(entityName);
    Assert.notNull(entityClass);

    this.entityName = entityName;
    this.entityClass = entityClass;
    this.idName = idName;
    Class<?> clazz = null;
    try {
      clazz = PropertyUtils.getPropertyType(entityClass, idName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (null != clazz) propertyTypes.put(idName, new IdentifierType(clazz));
  }

  public EntityType(String entityName, Class<?> entityClass, String idName, Type idType) {
    super();
    Assert.notNull(idName);
    Assert.notNull(entityName);
    Assert.notNull(entityClass);

    this.entityName = entityName;
    this.entityClass = entityClass;
    this.idName = idName;
    this.propertyTypes.put(idName, idType);
  }

  public EntityType(Class<?> entityClass) {
    this(entityClass.getName(), entityClass, "id");
  }

  public boolean isEntityType() {
    return true;
  }

  public Class<?> getEntityClass() {
    return entityClass;
  }

  public void setEntityClass(Class<?> entityClass) {
    this.entityClass = entityClass;
  }

  public Map<String, Type> getPropertyTypes() {
    return propertyTypes;
  }

  public void setPropertyTypes(Map<String, Type> propertyTypes) {
    this.propertyTypes = propertyTypes;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public Type getPropertyType(String property) {
    Type type = (Type) propertyTypes.get(property);
    if (null == type) {
      Class<?> propertyType = null;
      try {
        propertyType = PropertyUtils.getPropertyType(entityClass, property);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (null != propertyType) {
        if (Entity.class.isAssignableFrom(propertyType)) type = new EntityType(propertyType);
        if (propertyType.isInterface()) type = Model.getType(propertyType.getName());
        if (null == type) type = new IdentifierType(propertyType);
      }
    }
    if (null == type) logger.error("{} doesn't contains property {}", entityName, property);
    return type;
  }

  public String getEntityName() {
    return entityName;
  }

  public String getName() {
    return entityName;
  }

  public Class<?> getReturnedClass() {
    return entityClass;
  }

  public String getIdName() {
    return idName;
  }

  public void setIdName(String idName) {
    this.idName = idName;
  }

  @SuppressWarnings("unchecked")
  public Class<? extends Serializable> getIdType() {
    Type type = propertyTypes.get(idName);
    return (Class<? extends Serializable>) (null != type ? type.getReturnedClass() : null);
  }
}
