package com.ptsisi.common.query.builder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.ptsisi.common.collection.Order;
import com.ptsisi.common.collection.page.PageLimit;
import com.ptsisi.common.entity.util.EntityUtils;
import com.ptsisi.common.metadata.EntityType;
import com.ptsisi.common.metadata.Model;
import com.ptsisi.common.query.Lang;

public class OqlBuilder<T> extends AbstractQueryBuilder<T> {

  protected Class<T> entityClass;

  protected OqlBuilder() {
    super();
  }

  public static <E> OqlBuilder<E> hql(final String hql) {
    OqlBuilder<E> query = new OqlBuilder<E>();
    query.statement = hql;
    return query;
  }

  public static <E> OqlBuilder<E> from(final String from) {
    OqlBuilder<E> query = new OqlBuilder<E>();
    query.newFrom(from);
    return query;
  }

  @SuppressWarnings("unchecked")
  public static <E> OqlBuilder<E> from(final String entityName, final String alias) {
    EntityType type = Model.getType(entityName);
    OqlBuilder<E> query = new OqlBuilder<E>();
    if (null != type) query.entityClass = (Class<E>) type.getEntityClass();
    query.alias = alias;
    query.select = "select " + alias;
    query.from = StringUtils.join("from ", entityName, " ", alias);
    return query;
  }

  public static <E> OqlBuilder<E> from(final Class<E> entityClass) {
    EntityType type = Model.getType(entityClass.getName());
    if (null == type) type = Model.getType(entityClass);
    return from(entityClass, EntityUtils.getCommandName(type.getEntityName()));
  }

  @SuppressWarnings("unchecked")
  public static <E> OqlBuilder<E> from(final Class<E> entityClass, final String alias) {
    EntityType type = Model.getType(entityClass.getName());
    if (null == type) type = Model.getType(entityClass);
    OqlBuilder<E> query = new OqlBuilder<E>();
    query.entityClass = (Class<E>) type.getEntityClass();
    query.alias = alias;
    query.select = "select " + alias;
    query.from = StringUtils.join("from ", type.getEntityName(), " ", alias);
    return query;
  }

  public OqlBuilder<T> alias(final String alias) {
    this.alias = alias;
    return this;
  }

  public OqlBuilder<T> join(final String path, final String alias) {
    from = StringUtils.join(from, " join ", path, " ", alias);
    return this;
  }

  public OqlBuilder<T> join(final String joinMode, final String path, final String alias) {
    from = StringUtils.join(from, " ", joinMode, " join ", path, " ", alias);
    return this;
  }

  public OqlBuilder<T> params(final Map<String, Object> params) {
    this.params.putAll(params);
    return this;
  }

  public OqlBuilder<T> param(String name, Object value) {
    params.put(name, value);
    return this;
  }

  public OqlBuilder<T> limit(final PageLimit limit) {
    this.limit = limit;
    return this;
  }

  public OqlBuilder<T> limit(final int pageNo, final int pageSize) {
    this.limit = new PageLimit(pageNo, pageSize);
    return this;
  }

  public OqlBuilder<T> cacheable() {
    this.cacheable = true;
    return this;
  }

  public OqlBuilder<T> cacheable(final boolean cacheable) {
    this.cacheable = cacheable;
    return this;
  }

  public OqlBuilder<T> where(final Condition... conditions) {
    if (StringUtils.isNotEmpty(statement)) throw new RuntimeException("cannot add condition to a exists statement");
    return where(Arrays.asList(conditions));
  }

  public OqlBuilder<T> where(final String content) {
    Condition con = new Condition(content);
    return where(con);
  }

  public OqlBuilder<T> where(final String content, Object param1) {
    Condition con = new Condition(content);
    con.param(param1);
    return where(con);
  }

  public OqlBuilder<T> where(final String content, Object param1, Object param2) {
    Condition con = new Condition(content);
    con.param(param1);
    con.param(param2);
    return where(con);
  }

  public OqlBuilder<T> where(final String content, Object param1, Object param2, Object param3, Object... varparams) {
    Condition con = new Condition(content);
    con.param(param1);
    con.param(param2);
    con.param(param3);
    if (varparams != null && varparams.length > 0) con.params(Arrays.asList(varparams));
    return where(con);
  }

  public OqlBuilder<T> where(final Collection<Condition> cons) {
    conditions.addAll(cons);
    return params(Conditions.getParamMap(cons));
  }

  public OqlBuilder<T> orderBy(final String orderBy) {
    return orderBy(Order.parse(orderBy));
  }

  public OqlBuilder<T> orderBy(final int index, final String orderBy) {
    if (null != orders) {
      if (StringUtils.isNotEmpty(statement)) { throw new RuntimeException("cannot add order by to a exists statement."); }
      this.orders.addAll(index, Order.parse(orderBy));
    }
    return this;
  }

  public OqlBuilder<T> orderBy(final Order order) {
    if (null != order) { return orderBy(Collections.singletonList(order)); }
    return this;
  }

  public OqlBuilder<T> clearOrders() {
    this.orders.clear();
    return this;
  }

  public OqlBuilder<T> orderBy(final List<Order> orders) {
    if (null != orders) {
      if (StringUtils.isNotEmpty(statement)) { throw new RuntimeException("cannot add order by to a exists statement."); }
      this.orders.addAll(orders);
    }
    return this;
  }

  public OqlBuilder<T> select(final String what) {
    if (null == what) {
      this.select = null;
    } else {
      if (what.toLowerCase().trim().startsWith("select")) {
        this.select = what;
      } else {
        this.select = "select " + what;
      }
    }
    return this;
  }

  public OqlBuilder<T> newFrom(final String from) {
    if (null == from) {
      this.from = null;
    } else {
      if (StringUtils.contains(from.toLowerCase(), "from")) {
        this.from = from;
      } else {
        this.from = "from " + from;
      }
    }
    return this;
  }

  public OqlBuilder<T> groupBy(final String what) {
    if (StringUtils.isNotEmpty(what)) {
      groups.add(what);
    }
    return this;
  }

  public OqlBuilder<T> having(final String what) {
    Assert.isTrue(null != groups && !groups.isEmpty());
    if (StringUtils.isNotEmpty(what)) having = what;
    return this;
  }

  protected String genCountStatement() {
    StringBuilder countString = new StringBuilder("select count(*) ");
    // 原始查询语句
    final String genQueryStr = genQueryStatement(false);
    if (StringUtils.isEmpty(genQueryStr)) { return ""; }
    final String lowerCaseQueryStr = genQueryStr.toLowerCase();

    if (StringUtils.contains(lowerCaseQueryStr, " group ")) { return ""; }
    if (StringUtils.contains(lowerCaseQueryStr, " union ")) { return ""; }

    final int indexOfFrom = findIndexOfFrom(lowerCaseQueryStr);
    final String selectWhat = lowerCaseQueryStr.substring(0, indexOfFrom);
    final int indexOfDistinct = selectWhat.indexOf("distinct");
    // select distinct a from table;
    if (-1 != indexOfDistinct) {
      if (StringUtils.contains(selectWhat, ",")) {
        return "";
      } else {
        countString = new StringBuilder("select count(");
        countString.append(genQueryStr.substring(indexOfDistinct, indexOfFrom)).append(") ");
      }
    }

    int orderIdx = genQueryStr.lastIndexOf(" order ");
    if (-1 == orderIdx) orderIdx = genQueryStr.length();
    countString.append(genQueryStr.substring(indexOfFrom, orderIdx));
    return countString.toString();
  }

  private int findIndexOfFrom(String query) {
    if (query.startsWith("from")) return 0;
    int fromIdx = query.indexOf(" from ");
    if (-1 == fromIdx) return -1;
    final int first = query.substring(0, fromIdx).indexOf("(");
    if (first > 0) {
      int leftCnt = 1;
      int i = first + 1;
      while (leftCnt != 0 && i < query.length()) {
        if (query.charAt(i) == '(') leftCnt++;
        else if (query.charAt(i) == ')') leftCnt--;
        i++;
      }
      if (leftCnt > 0) return -1;
      else {
        fromIdx = query.indexOf(" from ", i);
        return (fromIdx == -1) ? -1 : fromIdx + 1;
      }
    } else {
      return fromIdx + 1;
    }
  }

  public OqlBuilder<T> forEntity(final Class<T> entityClass) {
    this.entityClass = entityClass;
    return this;
  }

  @Override
  protected Lang getLang() {
    return Lang.HQL;
  }

  public Class<T> getEntityClass() {
    return entityClass;
  }
}
