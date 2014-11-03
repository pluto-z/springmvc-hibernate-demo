package com.ptsisi.common.metadata;

public interface EntityContext {

  Type getType(String name);

  EntityType getEntityType(String entityName);

  EntityType getEntityType(Class<?> entityClass);

  String[] getEntityNames(Class<?> clazz);

  String getEntityName(Object obj);

}
