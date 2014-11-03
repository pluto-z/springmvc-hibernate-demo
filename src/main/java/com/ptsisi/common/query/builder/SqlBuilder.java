package com.ptsisi.common.query.builder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.ptsisi.common.collection.Order;
import com.ptsisi.common.collection.page.PageLimit;
import com.ptsisi.common.query.Lang;

public class SqlBuilder extends AbstractQueryBuilder<Object[]> {

  public static SqlBuilder sql(final String queryStr) {
    SqlBuilder sqlQuery = new SqlBuilder();
    sqlQuery.statement = queryStr;
    return sqlQuery;
  }

  protected String genCountStatement() {
    return "select count(*) from (" + genQueryStatement(false) + ")";
  }

  @Override
  protected Lang getLang() {
    return Lang.SQL;
  }

  public SqlBuilder alias(final String alias) {
    this.alias = alias;
    return this;
  }

  public SqlBuilder join(final String path, final String alias) {
    from += " join " + path + " " + alias;
    return this;
  }

  public SqlBuilder join(final String joinMode, final String path, final String alias) {
    from += " " + joinMode + " join " + path + " " + alias;
    return this;
  }

  public SqlBuilder params(final Map<String, Object> params) {
    this.params.putAll(params);
    return this;
  }

  public SqlBuilder param(String name, Object value) {
    params.put(name, value);
    return this;
  }

  public SqlBuilder limit(final PageLimit limit) {
    this.limit = limit;
    return this;
  }

  public SqlBuilder limit(final int pageNo, final int pageSize) {
    this.limit = new PageLimit(pageNo, pageSize);
    return this;
  }

  public SqlBuilder cacheable() {
    this.cacheable = true;
    return this;
  }

  public SqlBuilder cacheable(final boolean cacheable) {
    this.cacheable = cacheable;
    return this;
  }

  public SqlBuilder where(final Condition... cons) {
    if (StringUtils.isNotEmpty(statement)) throw new RuntimeException("cannot add condition to a exists statement");
    return where(Arrays.asList(cons));
  }

  public SqlBuilder where(final String content, Object... varparams) {
    Condition con = new Condition(content);
    con.params(Arrays.asList(varparams));
    return where(con);
  }

  public SqlBuilder where(final Collection<Condition> cons) {
    conditions.addAll(cons);
    return params(Conditions.getParamMap(cons));
  }

  public SqlBuilder orderBy(final String orderBy) {
    this.orders.addAll(Order.parse(orderBy));
    return this;
  }

  public SqlBuilder orderBy(final Order order) {
    if (null != order) {
      this.orders.add(order);
    }
    return this;
  }

  public SqlBuilder clearOrders() {
    this.orders.clear();
    return this;
  }

  public SqlBuilder orderBy(final List<Order> orders) {
    if (null != orders) {
      this.orders.addAll(orders);
    }
    return this;
  }

  public SqlBuilder select(final String what) {
    if (null == what) {
      this.select = null;
    } else {
      if (StringUtils.contains(what.toLowerCase(), "select")) {
        this.select = what;
      } else {
        this.select = "select " + what;
      }
    }
    return this;
  }

  public SqlBuilder newFrom(final String from) {
    if (null == from) {
      this.from = null;
    } else {
      if (StringUtils.contains(from.toLowerCase(), "from")) {
        this.from = from;
      } else {
        this.from = " from " + from;
      }
    }
    return this;
  }

  public SqlBuilder groupBy(final String what) {
    if (StringUtils.isNotEmpty(what)) groups.add(what);
    return this;
  }

  public SqlBuilder having(final String what) {
    Assert.isTrue(null != groups && !groups.isEmpty());
    if (StringUtils.isNotEmpty(what)) having = what;
    return this;
  }
}
