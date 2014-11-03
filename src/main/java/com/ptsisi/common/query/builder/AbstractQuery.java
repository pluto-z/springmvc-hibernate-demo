package com.ptsisi.common.query.builder;

import java.util.Map;

import com.ptsisi.common.collection.page.PageLimit;
import com.ptsisi.common.query.QueryBuilder;

public abstract class AbstractQuery<T> implements QueryBuilder<T> {

  protected String queryStr;

  protected String countStr;

  protected PageLimit limit;

  protected Map<String, Object> params;

  protected boolean cacheable = false;

  public PageLimit getLimit() {
    return limit;
  }

  public void setLimit(final PageLimit limit) {
    this.limit = limit;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public String getCountStr() {
    return countStr;
  }

  public void setCountStr(final String countStr) {
    this.countStr = countStr;
  }

  public String getQueryStr() {
    return queryStr;
  }

  public void setQueryStr(final String queryStr) {
    this.queryStr = queryStr;
  }

  public void setParams(final Map<String, Object> params) {
    this.params = params;
  }

  public abstract String toQueryString();

  public String toCountString() {
    return countStr;
  }

  public boolean isCacheable() {
    return cacheable;
  }

  public void setCacheable(final boolean cacheable) {
    this.cacheable = cacheable;
  }

}
