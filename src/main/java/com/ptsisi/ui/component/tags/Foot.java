package com.ptsisi.ui.component.tags;

import com.ptsisi.ui.component.UIBean;
import com.ptsisi.ui.component.ComponentContext;

public class Foot extends UIBean {

  private boolean needHead = true;
  
  public Foot(ComponentContext context) {
    super(context);
  }
  
  @Override
  protected void evaluateParams() {
    needHead = getRequest().getHeader("x-requested-with") == null && getRequestParameter("x-requested-with") == null;
  }
  
  public boolean isNeedHead() {
    return needHead;
  }

  public void setNeedHead(boolean needHead) {
    this.needHead = needHead;
  }
}
