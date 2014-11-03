package com.ptsisi.common.collection.page;

import java.util.List;

public interface Page<E> extends List<E> {

  public static final int DEFAULT_PAGE_NUM = 1;

  public static final int DEFAULT_PAGE_SIZE = 20;

  int getFirstPageNo();

  int getMaxPageNo();

  int getNextPageNo();

  int getPreviousPageNo();

  int getPageNo();

  int getPageSize();

  int getTotal();

  Page<E> next();

  boolean hasNext();

  Page<E> previous();

  boolean hasPrevious();

  Page<E> moveTo(int pageNo);

  List<E> getItems();

}
