package com.ptsisi.common.metadata;

import java.io.Serializable;

import com.ptsisi.common.Entity;
import com.ptsisi.common.metadata.impl.DefaultModelMeta;
import com.ptsisi.common.metadata.impl.SimpleEntityContext;

public final class Model {

  public static final String NULL = "null";

  public static DefaultModelMeta meta = new DefaultModelMeta();

  static {
    meta.setContext(new SimpleEntityContext());
  }

  private Model() {
  }

  public static <T extends Entity> T newInstance(final Class<T> clazz) {
    return meta.newInstance(clazz);
  }

  public static <T extends Entity> T newInstance(Class<T> clazz, Serializable id) {
    return meta.newInstance(clazz, id);
  }

  public static EntityType getType(String entityName) {
    return meta.getEntityType(entityName);
  }

  public static String getEntityName(Object obj) {
    return meta.getEntityName(obj);
  }

  public static EntityType getType(Class<?> clazz) {
    return meta.getEntityType(clazz);
  }

  public static void setMeta(DefaultModelMeta meta) {
    Model.meta = meta;
  }

}
