package com.ptsisi.common.query.builder;

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
import com.ptsisi.common.query.QueryBuilder;

public abstract class AbstractQueryBuilder<T> implements QueryBuilder<T> {

  public static final String INNER_JOIN = " left join ";

  public static final String OUTER_JOIN = " outer join ";

  public static final String LEFT_OUTER_JOIN = " left outer join ";

  public static final String RIGHT_OUTER_JOIN = " right outer join ";

  protected String statement;

  protected PageLimit limit;

  protected Map<String, Object> params = Maps.newHashMap();

  protected String select;

  protected String from;

  protected String alias;

  protected List<Condition> conditions = Lists.newArrayList();

  protected List<Order> orders = Lists.newArrayList();

  protected List<String> groups = Lists.newArrayList();

  protected String having;

  protected boolean cacheable = false;

  public Map<String, Object> getParams() {
    return Maps.newHashMap(params);
  }

  public String getAlias() {
    return alias;
  }

  public boolean isCacheable() {
    return cacheable;
  }

  public PageLimit getLimit() {
    return limit;
  }

  public Query<T> build() {
    QueryBean<T> queryBean = new QueryBean<T>();
    queryBean.setStatement(genStatement());
    queryBean.setParams(getParams());
    if (null != limit) {
      queryBean.setLimit(new PageLimit(limit.getPageNo(), limit.getPageSize()));
    }
    queryBean.setCountStatement(genCountStatement());
    queryBean.setCacheable(cacheable);
    queryBean.setLang(getLang());
    return queryBean;
  }

  abstract protected Lang getLang();

  protected String genStatement() {
    if (StringUtils.isNotEmpty(statement)) return statement;
    else return genQueryStatement(true);
  }

  abstract protected String genCountStatement();

  protected String genQueryStatement(final boolean hasOrder) {
    if (null == from) return statement;
    final StringBuilder buf = new StringBuilder(50);
    buf.append((select == null) ? "" : (select + " ")).append(from);
    if (!conditions.isEmpty()) buf.append(" where ").append(Conditions.toQueryString(conditions));

    if (!groups.isEmpty()) {
      buf.append(" group by ");
      for (final String groupBy : groups)
        buf.append(groupBy).append(',');
      buf.deleteCharAt(buf.length() - 1);
    }
    if (hasOrder && !CollectionUtils.isEmpty(orders)) buf.append(' ').append(Order.toSortString(orders));

    if (null != having) buf.append(" having ").append(having);
    return buf.toString();
  }

  public List<Condition> getConditions() {
    return conditions;
  }

}
