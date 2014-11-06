package com.ptsisi.ui.component.tags;

import com.ptsisi.ui.component.UIBean;
import com.ptsisi.ui.component.ComponentContext;

public class Head extends UIBean {

  private boolean compressed = true;

  private boolean needHead = true;

  private String title;

  public Head(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    compressed = getBooleanValue(getRequestParameter("devMode"));
    needHead = getRequest().getHeader("x-requested-with") == null && getRequestParameter("x-requested-with") == null;
  }

  public boolean isNeedHead() {
    return needHead;
  }

  public void setNeedHead(boolean needHead) {
    this.needHead = needHead;
  }

  public boolean isCompressed() {
    return compressed;
  }

  public void setCompressed(boolean compress) {
    this.compressed = compress;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
