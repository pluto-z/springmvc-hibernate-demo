package com.ptsisi.ui.component.tags;

import com.ptsisi.ui.component.ComponentContext;

public class Password extends AbstractTextBean {

  protected String minlength;

  protected String showStrength = "false";

  @Override
  protected void evaluateParams() {
    super.evaluateParams();
    Form myform = findAncestor(Form.class);
    if (null != myform) {
      myform.addCheck(id, "minLength("+minlength+")");
      myform.addCheck(id, "maxLength("+maxlength+")");
    }
  }

  public Password(ComponentContext context) {
    super(context);
    minlength = "6";
    maxlength = "10";
  }

  public String getMinlength() {
    return minlength;
  }

  public void setMinlength(String minlength) {
    this.minlength = minlength;
  }

  public String getShowStrength() {
    return showStrength;
  }

  public void setShowStrength(String showStrength) {
    this.showStrength = showStrength;
  }
}
