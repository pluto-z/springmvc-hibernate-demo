package com.ptsisi.common.metadata;

public interface Type {

  boolean isCollectionType();

  boolean isComponentType();

  boolean isEntityType();

  Type getPropertyType(String property);

  String getName();

  Class<?> getReturnedClass();

  Object newInstance();
}
