package com.ptsisi.common.query;

import com.ptsisi.common.collection.page.PageLimit;

public interface LimitQuery<T> extends Query<T> {

  PageLimit getLimit();

  LimitQuery<T> limit(final PageLimit limit);

  Query<T> getCountQuery();
}
