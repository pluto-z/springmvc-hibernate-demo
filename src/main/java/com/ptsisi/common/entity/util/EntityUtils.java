package com.ptsisi.common.entity.util;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.ptsisi.common.Entity;

public final class EntityUtils {

  private static final Logger logger = LoggerFactory.getLogger(EntityUtils.class);

  private EntityUtils() {
  }

  public static <T extends Entity> List<?> extractIds(Collection<T> entities) {
    List<Object> idList = Lists.newArrayList();
    for (Iterator<T> iter = entities.iterator(); iter.hasNext();) {
      Entity element = iter.next();
      try {
        idList.add(PropertyUtils.getProperty(element, "id"));
      } catch (Exception e) {
        logger.error("getProperty error", e);
        continue;
      }
    }
    return idList;
  }

  public static String getCommandName(Class<?> clazz) {
    String name = clazz.getName();
    return StringUtils.uncapitalize(name.substring(name.lastIndexOf('.') + 1));
  }

  public static String getCommandName(String entityName) {
    return StringUtils.uncapitalize(StringUtils.substringAfterLast(entityName, "."));
  }

  public static String getCommandName(Object obj) {
    String name = obj.getClass().getName();
    int dollar = name.indexOf('$');
    if (-1 == dollar) {
      name = name.substring(name.lastIndexOf('.') + 1);
    } else {
      name = name.substring(name.lastIndexOf('.') + 1, dollar);
    }
    return StringUtils.uncapitalize(name);
  }

  public static <T extends Entity> String extractIdSeq(Collection<T> entities) {
    if (null == entities || entities.isEmpty()) { return ""; }
    StringBuilder idBuf = new StringBuilder(",");
    for (Iterator<T> iter = entities.iterator(); iter.hasNext();) {
      T element = iter.next();
      try {
        idBuf.append(PropertyUtils.getProperty(element, "id"));
        idBuf.append(',');
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }
    return idBuf.toString();
  }

  public static boolean isEmpty(Entity entity, boolean ignoreDefault) {
    try {
      for (final PropertyDescriptor descriptor : PropertyUtils.getPropertyDescriptors(entity.getClass())) {
        if (descriptor.getWriteMethod() != null) {
          Object value = PropertyUtils.getProperty(entity, descriptor.getName());
          if (null == value) continue;
          if (ignoreDefault) {
            if (value instanceof Number) {
              if (((Number) value).intValue() != 0) { return false; }
            } else if (value instanceof String) {
              String str = (String) value;
              if (StringUtils.isNotEmpty(str)) { return false; }
            } else {
              return false;
            }
          } else {
            return false;
          }
        }
      }
    } catch (Exception e) {
      logger.error("isEmpty error", e);
    }
    return true;
  }

  public static String getEntityClassName(Class<?> clazz) {
    String name = clazz.getName();
    int dollar = name.indexOf('$');
    if (-1 == dollar) {
      return name;
    } else {
      return name.substring(0, dollar);
    }
  }

}
