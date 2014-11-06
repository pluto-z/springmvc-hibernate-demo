package com.ptsisi.ui.component.tags;

import com.ptsisi.ui.component.ComponentContext;

public class Email extends AbstractTextBean {

  public Email(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    super.evaluateParams();
    Form myform = findAncestor(Form.class);
    if (null != myform) {
      myform.addCheck(id, "match('email')");
    }
  }
}
