package com.ptsisi.common.query;

import java.util.Map;

import com.ptsisi.common.collection.page.PageLimit;

public interface QueryBuilder<T> {

  Query<T> build();

  QueryBuilder<T> limit(PageLimit limit);

  Map<String, Object> getParams();

  QueryBuilder<T> params(Map<String, Object> newParams);
}
