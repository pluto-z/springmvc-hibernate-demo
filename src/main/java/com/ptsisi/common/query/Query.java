package com.ptsisi.common.query;

import java.util.Map;

public interface Query<T> {

  String getStatement();

  Map<String, Object> getParams();

  boolean isCacheable();

  Lang getLang();
}
