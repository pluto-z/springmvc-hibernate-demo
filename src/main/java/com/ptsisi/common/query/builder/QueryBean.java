package com.ptsisi.common.query.builder;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ptsisi.common.collection.page.PageLimit;
import com.ptsisi.common.query.Lang;
import com.ptsisi.common.query.LimitQuery;
import com.ptsisi.common.query.Query;

public class QueryBean<T> implements LimitQuery<T> {

  private Lang lang;

  private String statement;

  private String countStatement;

  private PageLimit limit;

  private boolean cacheable;

  private Map<String, Object> params;

  public Query<T> getCountQuery() {
    if (StringUtils.isEmpty(countStatement)) { return null; }
    QueryBean<T> bean = new QueryBean<T>();
    bean.setStatement(countStatement);
    bean.setLang(lang);
    bean.setParams(params);
    bean.setCacheable(cacheable);
    return bean;
  }

  public String getStatement() {
    return statement;
  }

  public String getCountStatement() {
    return countStatement;
  }

  public PageLimit getLimit() {
    return limit;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public void setStatement(String statement) {
    this.statement = statement;
  }

  public void setCountStatement(String countStatement) {
    this.countStatement = countStatement;
  }

  public void setLimit(PageLimit limit) {
    this.limit = limit;
  }

  public LimitQuery<T> limit(PageLimit limit) {
    this.limit = new PageLimit(limit.getPageNo(), limit.getPageSize());
    return this;
  }

  public void setCacheable(boolean cacheable) {
    this.cacheable = cacheable;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }

  public boolean isCacheable() {
    return cacheable;
  }

  public Lang getLang() {
    return lang;
  }

  public void setLang(Lang lang) {
    this.lang = lang;
  }

}
