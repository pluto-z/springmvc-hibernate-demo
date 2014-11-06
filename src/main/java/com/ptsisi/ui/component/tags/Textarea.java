package com.ptsisi.ui.component.tags;

import com.ptsisi.ui.component.ComponentContext;

public class Textarea extends AbstractTextBean {

  protected String cols;
  protected String readonly;
  protected String rows;
  protected String wrap;

  public Textarea(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    super.evaluateParams();
    Form myform = findAncestor(Form.class);
    if (myform != null) {
      if (null != maxlength) myform.addCheck(id, "maxLength(" + maxlength + ")");
    }
  }

  public String getCols() {
    return cols;
  }

  public void setCols(String cols) {
    this.cols = cols;
  }

  public String getReadonly() {
    return readonly;
  }

  public void setReadonly(String readonly) {
    this.readonly = readonly;
  }

  public String getRows() {
    return rows;
  }

  public void setRows(String rows) {
    this.rows = rows;
  }

  public String getWrap() {
    return wrap;
  }

  public void setWrap(String wrap) {
    this.wrap = wrap;
  }
}
