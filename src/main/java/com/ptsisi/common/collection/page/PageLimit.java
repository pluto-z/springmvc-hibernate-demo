package com.ptsisi.common.collection.page;

public class PageLimit {

  private int pageNo;

  private int pageSize;

  public PageLimit() {
    super();
  }

  public PageLimit(final int pageNo, final int pageSize) {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(final int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPageNo() {
    return pageNo;
  }

  public void setPageNo(final int pageNo) {
    this.pageNo = pageNo;
  }

  public boolean isValid() {
    return pageNo > 0 && pageSize > 0;
  }

  public String toString() {
    return new StringBuilder().append("pageNo:").append(pageNo).append(" pageSize:").append(pageSize).toString();
  }

}
