package com.ptsisi.common.metadata;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.common.collect.Maps;

public class ComponentType extends AbstractType {

  private Class<?> componentClass;

  private final Map<String, Type> propertyTypes = Maps.newHashMap();

  public boolean isComponentType() {
    return true;
  }

  public String getName() {
    return componentClass.toString();
  }

  public Class<?> getReturnedClass() {
    return componentClass;
  }

  public ComponentType() {
    super();
  }

  public ComponentType(Class<?> componentClass) {
    super();
    this.componentClass = componentClass;
  }

  public Type getPropertyType(String propertyName) {
    Type type = (Type) propertyTypes.get(propertyName);
    if (null == type) {
      Class<?> propertyType = null;
      try {
        propertyType = PropertyUtils.getPropertyType(componentClass, propertyName);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (null != propertyType) { return new IdentifierType(propertyType); }
    }
    return type;
  }

  public Map<String, Type> getPropertyTypes() {
    return propertyTypes;
  }

}
