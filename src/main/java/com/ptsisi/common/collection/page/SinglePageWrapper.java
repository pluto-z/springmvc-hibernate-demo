package com.ptsisi.common.collection.page;

import java.io.Serializable;
import java.util.List;

public class SinglePageWrapper implements Serializable {

  private static final long serialVersionUID = 5291220472271881872L;

  private int total;

  private List<?> rows;

  public SinglePageWrapper() {
    super();
  }

  public SinglePageWrapper(int total, List<?> rows) {
    this.total = total;
    this.rows = rows;
  }

  /**
   * @return the total
   */
  public int getTotal() {
    return total;
  }

  /**
   * @param total the total to set
   */
  public void setTotal(int total) {
    this.total = total;
  }

  /**
   * @return the rows
   */
  public List<?> getRows() {
    return rows;
  }

  /**
   * @param rows the rows to set
   */
  public void setRows(List<?> rows) {
    this.rows = rows;
  }

}
