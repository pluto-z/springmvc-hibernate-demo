package com.ptsisi.common.query.builder;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.ptsisi.common.Entity;
import com.ptsisi.common.entity.Component;
import com.ptsisi.common.predicate.ValidEntityKeyPredicate;

public final class Conditions {

  private static final Logger logger = LoggerFactory.getLogger(Conditions.class);

  private Conditions() {
    super();
  }

  public static Condition and(Condition... conditions) {
    return concat(Arrays.asList(conditions), "and");
  }

  public static Condition and(List<Condition> conditions) {
    return concat(conditions, "and");
  }

  public static Condition or(Condition... conditions) {
    return concat(Arrays.asList(conditions), "or");
  }

  public static Condition or(List<Condition> conditions) {
    return concat(conditions, "or");
  }

  static Condition concat(List<Condition> conditions, String andor) {
    Assert.isTrue(!conditions.isEmpty(), "conditions shouldn't be empty!");
    if (conditions.size() == 1) return conditions.get(0);
    StringBuffer sb = new StringBuffer();
    List<Object> params = Lists.newArrayList();
    sb.append("(");
    for (Condition con : conditions) {
      sb.append(" " + andor + " (");
      sb.append(con.getContent());
      sb.append(')');
      params.addAll(con.getParams());
    }
    sb.append(")");
    sb.replace(0, (" " + andor + " ").length() + 1, "(");
    return new Condition(sb.toString()).params(params);
  }

  public static String toQueryString(final List<Condition> conditions) {
    if (null == conditions || conditions.isEmpty()) { return ""; }
    final StringBuilder buf = new StringBuilder("");
    for (final Iterator<Condition> iter = conditions.iterator(); iter.hasNext();) {
      final Condition con = iter.next();
      buf.append('(').append(con.getContent()).append(')');
      if (iter.hasNext()) {
        buf.append(" and ");
      }
    }
    return buf.toString();
  }

  public static List<Condition> extractConditions(final String alias, final Entity entity) {
    if (null == entity) { return Collections.emptyList(); }
    final List<Condition> conditions = new ArrayList<Condition>();

    StringBuilder aliasBuilder = new StringBuilder(alias == null ? "" : alias);
    if (aliasBuilder.length() > 0 && !alias.endsWith(".")) aliasBuilder.append(".");
    String attr = "";
    try {
      final PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(entity.getClass());
      for (PropertyDescriptor descriptor : props) {
        if (descriptor.getWriteMethod() != null) {
          attr = descriptor.getName();
          final Object value = PropertyUtils.getProperty(entity, attr);
          if (null == value) continue;
          if (!(value instanceof Collection<?>)) addAttrCondition(conditions, alias + attr, value);
        }
      }
    } catch (Exception e) {
      logger.debug("error occur in extractConditions for  bean {} with attr named {}", entity, attr);
    }
    return conditions;
  }

  public static Map<String, Object> getParamMap(final Collection<Condition> conditions) {
    final Map<String, Object> params = new HashMap<String, Object>();
    for (final Condition con : conditions) {
      params.putAll(getParamMap(con));
    }
    return params;
  }

  public static Map<String, Object> getParamMap(final Condition condition) {
    final Map<String, Object> params = new HashMap<String, Object>();
    if (!StringUtils.contains(condition.getContent(), "?")) {
      final List<String> paramNames = condition.getParamNames();
      for (int i = 0; i < paramNames.size(); i++) {
        if (i >= condition.getParams().size()) break;
        params.put(paramNames.get(i), condition.getParams().get(i));
      }
    }
    return params;
  }

  private static void addAttrCondition(final List<Condition> conditions, final String name, Object value) {
    if (value instanceof String) {
      if (StringUtils.isBlank((String) value)) { return; }
      StringBuilder content = new StringBuilder(name);
      content.append(" like :").append(name.replace('.', '_'));
      conditions.add(new Condition(content.toString(), "%" + value + "%"));
    } else if (value instanceof Component) {
      conditions.addAll(extractComponent(name, (Component) value));
      return;
    } else if (value instanceof Entity) {
      try {
        final String key = "id";
        Object property = PropertyUtils.getProperty(value, key);
        if (ValidEntityKeyPredicate.Instance.evaluate(property)) {
          StringBuilder content = new StringBuilder(name);
          content.append('.').append(key).append(" = :").append(name.replace('.', '_')).append('_').append(key);
          conditions.add(new Condition(content.toString(), property));
        }
      } catch (Exception e) {
        logger.warn("getProperty " + value + "error", e);
      }
    } else {
      conditions.add(new Condition(name + " = :" + name.replace('.', '_'), value));
    }
  }

  private static List<Condition> extractComponent(final String prefix, final Component component) {
    if (null == component) { return Collections.emptyList(); }
    final List<Condition> conditions = Lists.newArrayList();
    String attr = "";
    try {
      final PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(component.getClass());
      for (PropertyDescriptor descriptor : props) {
        if (descriptor.getWriteMethod() != null) {
          attr = descriptor.getName();
          final Object value = PropertyUtils.getProperty(component, attr);
          if (value == null) {
            continue;
          } else if (value instanceof Collection<?>) {
            if (((Collection<?>) value).isEmpty()) continue;
          } else {
            addAttrCondition(conditions, prefix + "." + attr, value);
          }
        }
      }
    } catch (Exception e) {
      logger.warn("error occur in extractComponent of component:" + component + "with attr named :" + attr);
    }
    return conditions;
  }

}
