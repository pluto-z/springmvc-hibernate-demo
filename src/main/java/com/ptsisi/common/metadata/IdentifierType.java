package com.ptsisi.common.metadata;

public class IdentifierType extends AbstractType {

  private Class<?> clazz;

  public IdentifierType() {
    super();
  }

  public IdentifierType(Class<?> clazz) {
    super();
    this.clazz = clazz;
  }

  public String getName() {
    return clazz.toString();
  }

  public Class<?> getReturnedClass() {
    return clazz;
  }

  public boolean isCollectionType() {
    return false;
  }

  public boolean isComponentType() {
    return false;
  }

  public boolean isEntityType() {
    return false;
  }

}
