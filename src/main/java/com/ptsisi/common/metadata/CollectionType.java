package com.ptsisi.common.metadata;

import java.lang.reflect.Array;

public class CollectionType extends AbstractType {

  public boolean isCollectionType() {
    return true;
  }

  private Class<?> collectionClass;

  private Type elementType;

  private Class<?> indexClass;

  private boolean array = false;;

  public String getName() {
    StringBuilder buffer = new StringBuilder();
    if (null != collectionClass) {
      buffer.append(collectionClass.getName());
    }
    buffer.append(':');
    if (null != indexClass) {
      buffer.append(indexClass.getName());
    }
    buffer.append(':');
    buffer.append(elementType.getName());
    return buffer.toString();
  }

  public Type getPropertyType(String property) {
    return elementType;
  }

  public Type getElementType() {
    return elementType;
  }

  public void setElementType(Type elementType) {
    this.elementType = elementType;
  }

  public Class<?> getIndexClass() {
    return indexClass;
  }

  public boolean hasIndex() {
    return (null != indexClass) && (indexClass.equals(int.class));
  }

  public Class<?> getReturnedClass() {
    return collectionClass;
  }

  public Class<?> getCollectionClass() {
    return collectionClass;
  }

  public void setCollectionClass(Class<?> collectionClass) {
    this.collectionClass = collectionClass;
  }

  public boolean isArray() {
    return array;
  }

  public void setArray(boolean isArray) {
    this.array = isArray;
  }

  public Object newInstance() {
    if (array) {
      return Array.newInstance(elementType.getReturnedClass(), 0);
    } else {
      return super.newInstance();
    }
  }
}
