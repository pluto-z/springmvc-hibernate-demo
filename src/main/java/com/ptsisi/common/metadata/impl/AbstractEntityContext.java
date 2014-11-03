package com.ptsisi.common.metadata.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ptsisi.common.Entity;
import com.ptsisi.common.metadata.EntityContext;
import com.ptsisi.common.metadata.EntityType;
import com.ptsisi.common.metadata.IdentifierType;
import com.ptsisi.common.metadata.Type;

public abstract class AbstractEntityContext implements EntityContext {

  protected Map<String, EntityType> entityTypes = Maps.newHashMap();

  protected Map<String, EntityType> classEntityTypes = Maps.newHashMap();

  protected static final Logger logger = LoggerFactory.getLogger(AbstractEntityContext.class);

  public String[] getEntityNames(Class<?> clazz) {
    return new String[0];
  }

  public Type getType(String name) {
    Type type = getEntityType(name);
    if (null == type) {
      try {
        return new IdentifierType(Class.forName(name));
      } catch (ClassNotFoundException e) {
        logger.error("system doesn't contains entity {}", name);
      }
      return null;
    } else {
      return type;
    }
  }

  public String getEntityName(Object obj) {
    EntityType type = getEntityType(obj.getClass());
    if (null != type) {
      return type.getEntityName();
    } else {
      return null;
    }
  }

  public EntityType getEntityType(Class<?> entityClass) {
    String className = entityClass.getName();
    EntityType type = entityTypes.get(className);
    if (null != type) { return type; }

    type = classEntityTypes.get(className);
    if (null == type) {
      List<EntityType> matched = Lists.newArrayList();
      for (EntityType entityType : entityTypes.values()) {
        if (className.equals(entityType.getEntityName()) || className.equals(entityType.getEntityClass().getName())) {
          matched.add(entityType);
        }
      }
      if (matched.size() > 1) { throw new RuntimeException("multi-entityName for class:" + className); }
      if (matched.isEmpty()) {
        EntityType tmp = new EntityType(entityClass);
        classEntityTypes.put(className, tmp);
        return tmp;
      } else {
        classEntityTypes.put(className, matched.get(0));
        type = (EntityType) matched.get(0);
      }
    }
    return type;
  }

  public EntityType getEntityType(String entityName) {
    EntityType type = entityTypes.get(entityName);
    if (null != type) { return type; }
    type = classEntityTypes.get(entityName);
    // last try by it's interface
    if (null == type) {
      try {
        Class<?> entityClass = ClassLoader.getSystemClassLoader().loadClass(entityName);
        if (Entity.class.isAssignableFrom(entityClass)) type = new EntityType(entityClass);
        else logger.warn("{} 's is not entity", entityClass);
      } catch (Exception e) {
        logger.error("system doesn't contains entity {}", entityName);
      }
    }
    return type;
  }
}
