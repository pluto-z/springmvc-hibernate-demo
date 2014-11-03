package com.ptsisi.hibernate;

import java.util.*;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ptsisi.common.metadata.CollectionType;
import com.ptsisi.common.metadata.ComponentType;
import com.ptsisi.common.metadata.EntityType;
import com.ptsisi.common.metadata.IdentifierType;
import com.ptsisi.common.metadata.Type;
import com.ptsisi.common.metadata.impl.AbstractEntityContext;

public class HibernateEntityContext extends AbstractEntityContext {

  private final Logger logger = LoggerFactory.getLogger(HibernateEntityContext.class);

  private final Map<String, CollectionType> collectionTypes = Maps.newHashMap();

  @Override
  public String getEntityName(Object target) {
    if (target instanceof HibernateProxy) {
      return ((HibernateProxy) target).getHibernateLazyInitializer().getEntityName();
    } else {
      return getEntityType(target.getClass()).getEntityName();
    }
  }

  public void initFrom(SessionFactory sessionFactory) {
    Assert.notNull(sessionFactory);
    Stopwatch watch = Stopwatch.createStarted();
    Map<String, ClassMetadata> classMetadatas = sessionFactory.getAllClassMetadata();
    int entityCount = entityTypes.size();
    int collectionCount = collectionTypes.size();
    for (Iterator<ClassMetadata> iter = classMetadatas.values().iterator(); iter.hasNext();) {
      ClassMetadata cm = (ClassMetadata) iter.next();
      buildEntityType(sessionFactory, cm.getEntityName());
    }
    logger.info("Find {} entities,{} collections from hibernate in {}", new Object[] {
        entityTypes.size() - entityCount, collectionTypes.size() - collectionCount, watch });
    if (logger.isDebugEnabled()) loggerTypeInfo();
    collectionTypes.clear();
  }

  private void loggerTypeInfo() {
    List<String> names = Lists.newArrayList(entityTypes.keySet());
    Collections.sort(names);
    for (final String entityName : names) {
      EntityType entityType = (EntityType) entityTypes.get(entityName);
      logger.debug("entity:{}-->{}", entityType.getEntityName(), entityType.getEntityClass().getName());
      logger.debug("propertyType size:{}", Integer.valueOf(entityType.getPropertyTypes().size()));
    }
    names.clear();
    names.addAll(collectionTypes.keySet());
    Collections.sort(names);
    for (final String collectionName : names) {
      CollectionType collectionType = (CollectionType) collectionTypes.get(collectionName);
      logger.debug("collection:{}", collectionType.getName());
      logger.debug("class:{}", collectionType.getCollectionClass());
      logger.debug("elementType:{}", collectionType.getElementType().getReturnedClass());
    }
  }

  private EntityType buildEntityType(SessionFactory sessionFactory, String entityName) {
    EntityType entityType = (EntityType) entityTypes.get(entityName);
    if (null == entityType) {
      ClassMetadata cm = sessionFactory.getClassMetadata(entityName);
      if (null == cm) {
        logger.error("Cannot find ClassMetadata for {}", entityName);
        return null;
      }
      entityType = new EntityType(cm.getEntityName(), cm.getMappedClass(), cm.getIdentifierPropertyName(),
          new IdentifierType(cm.getIdentifierType().getReturnedClass()));
      entityTypes.put(cm.getEntityName(), entityType);

      Map<String, Type> propertyTypes = entityType.getPropertyTypes();
      String[] ps = cm.getPropertyNames();
      for (int i = 0; i < ps.length; i++) {
        org.hibernate.type.Type type = cm.getPropertyType(ps[i]);
        if (type.isEntityType()) {
          propertyTypes.put(ps[i], buildEntityType(sessionFactory, type.getName()));
        } else if (type.isComponentType()) {
          propertyTypes.put(ps[i], buildComponentType(sessionFactory, entityName, ps[i]));
        } else if (type.isCollectionType()) {
          propertyTypes.put(ps[i],
              buildCollectionType(sessionFactory, defaultCollectionClass(type), entityName + "." + ps[i]));
        }
      }
    }
    return entityType;
  }

  private CollectionType buildCollectionType(SessionFactory sessionFactory, Class<?> collectionClass, String role) {
    CollectionMetadata cm = sessionFactory.getCollectionMetadata(role);
    if (null == cm) return null;
    org.hibernate.type.Type type = cm.getElementType();
    EntityType elementType = null;
    if (type.isEntityType()) {
      elementType = (EntityType) entityTypes.get(type.getName());
      if (null == elementType) elementType = buildEntityType(sessionFactory, type.getName());
    } else {
      elementType = new EntityType(type.getReturnedClass());
    }

    CollectionType collectionType = new CollectionType();
    collectionType.setElementType(elementType);
    collectionType.setArray(cm.isArray());
    collectionType.setCollectionClass(collectionClass);
    if (!collectionTypes.containsKey(collectionType.getName())) {
      collectionTypes.put(collectionType.getName(), collectionType);
    }
    return collectionType;
  }

  private ComponentType buildComponentType(SessionFactory sessionFactory, String entityName, String propertyName) {
    EntityType entityType = (EntityType) entityTypes.get(entityName);
    if (null != entityType) {
      Type propertyType = (Type) entityType.getPropertyTypes().get(propertyName);
      if (null != propertyType) { return (ComponentType) propertyType; }
    }

    ClassMetadata cm = sessionFactory.getClassMetadata(entityName);
    org.hibernate.type.ComponentType hcType = (org.hibernate.type.ComponentType) cm.getPropertyType(propertyName);
    String[] propertyNames = hcType.getPropertyNames();

    ComponentType cType = new ComponentType(hcType.getReturnedClass());
    Map<String, Type> propertyTypes = cType.getPropertyTypes();
    for (int j = 0; j < propertyNames.length; j++) {
      org.hibernate.type.Type type = cm.getPropertyType(propertyName + "." + propertyNames[j]);
      if (type.isEntityType()) {
        propertyTypes.put(propertyNames[j], buildEntityType(sessionFactory, type.getName()));
      } else if (type.isComponentType()) {
        propertyTypes.put(propertyNames[j],
            buildComponentType(sessionFactory, entityName, propertyName + "." + propertyNames[j]));
      } else if (type.isCollectionType()) {
        propertyTypes.put(
            propertyNames[j],
            buildCollectionType(sessionFactory, defaultCollectionClass(type), entityName + "." + propertyName + "."
                + propertyNames[j]));
      }
    }
    return cType;
  }

  private Class<?> defaultCollectionClass(org.hibernate.type.Type collectionType) {
    if (collectionType.isAnyType()) {
      return null;
    } else if (org.hibernate.type.SetType.class.isAssignableFrom(collectionType.getClass())) {
      return HashSet.class;
    } else if (org.hibernate.type.MapType.class.isAssignableFrom(collectionType.getClass())) {
      return HashMap.class;
    } else {
      return ArrayList.class;
    }
  }

}
