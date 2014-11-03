package com.ptsisi.common.query.builder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ptsisi.common.collection.Order;
import com.ptsisi.common.collection.page.PageLimit;
import com.ptsisi.common.query.Lang;
import com.ptsisi.common.query.Query;

public class SqlQuery extends AbstractQuery<Object> {

  public static final String INNER_JOIN = " left join ";

  public static final String OUTER_JOIN = " outer join ";

  public static final String LEFT_OUTER_JOIN = " left outer join ";

  public static final String RIGHT_OUTER_JOIN = " right outer join ";

  protected String select;

  protected String from;

  protected List<Condition> conditions = Lists.newArrayList();

  protected List<Order> orders = Lists.newArrayList();

  protected List<String> groups = Lists.newArrayList();

  public SqlQuery() {
    super();
  }

  public SqlQuery(final String queryStr) {
    super();
    this.queryStr = queryStr;
  }

  public SqlQuery add(final Condition condition) {
    conditions.add(condition);
    return this;
  }

  public SqlQuery add(final Collection<Condition> cons) {
    conditions.addAll(cons);
    return this;
  }

  public SqlQuery addOrder(final Order order) {
    if (null != order) {
      this.orders.add(order);
    }
    return this;
  }

  public SqlQuery addOrder(final List<Order> orders) {
    if (null != orders) {
      this.orders.addAll(orders);
    }
    return this;
  }

  public String getSelect() {
    return select;
  }

  public void setSelect(final String select) {
    if (null == select) {
      this.select = null;
    } else {
      if (StringUtils.contains(select.toLowerCase(), "select")) {
        this.select = select;
      } else {
        this.select = "select " + select;
      }
    }
  }

  public List<Condition> getConditions() {
    return conditions;
  }

  public void setConditions(final List<Condition> conditions) {
    this.conditions = conditions;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(final String from) {
    if (null == from) {
      this.from = null;
    } else {
      if (StringUtils.contains(from.toLowerCase(), "from")) {
        this.from = from;
      } else {
        this.from = " from " + from;
      }
    }
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(final List<Order> orders) {
    this.orders = orders;
  }

  public List<String> getGroups() {
    return groups;
  }

  public void setGroups(final List<String> groups) {
    this.groups = groups;
  }

  public SqlQuery groupBy(final String what) {
    if (StringUtils.isNotEmpty(what)) {
      groups.add(what);
    }
    return this;
  }

  public String toQueryString() {
    if (StringUtils.isNotEmpty(queryStr)) {
      return queryStr;
    } else {
      return genQueryString(true);
    }
  }

  public String toCountString() {
    if (StringUtils.isNotEmpty(countStr)) {
      return countStr;
    } else {
      return "select count(*) from (" + genQueryString(false) + ")";
    }
  }

  protected String genQueryString(final boolean hasOrder) {
    if (null == from) { return queryStr; }
    final StringBuilder buf = new StringBuilder(50);
    buf.append((select == null) ? "" : select).append(' ').append(from);
    if (!conditions.isEmpty()) {
      buf.append(" where ").append(Conditions.toQueryString(conditions));
    }
    if (!groups.isEmpty()) {
      buf.append(" group by ");
      for (final String groupBy : groups) {
        buf.append(groupBy).append(',');
      }
      buf.deleteCharAt(buf.length() - 1);
    }
    if (hasOrder && !CollectionUtils.isEmpty(orders)) {
      buf.append(' ').append(Order.toSortString(orders));
    }
    return buf.toString();
  }

  public Map<String, Object> getParams() {
    return (null == params) ? Conditions.getParamMap(conditions) : Maps.newHashMap(params);
  }

  public Query<Object> build() {
    QueryBean<Object> queryBean = new QueryBean<Object>();
    queryBean.setStatement(toQueryString());
    queryBean.setParams(Maps.newHashMap(getParams()));
    if (null != limit) {
      queryBean.setLimit(new PageLimit(limit.getPageNo(), limit.getPageSize()));
    }
    queryBean.setCountStatement(toCountString());
    queryBean.setCacheable(cacheable);
    queryBean.setLang(getLang());
    return queryBean;
  }

  protected Lang getLang() {
    return Lang.SQL;
  }

  public SqlQuery limit(PageLimit limit) {
    this.limit = limit;
    return this;
  }

  public SqlQuery params(Map<String, Object> newParams) {
    this.params = Maps.newHashMap(newParams);
    return this;
  }

}
