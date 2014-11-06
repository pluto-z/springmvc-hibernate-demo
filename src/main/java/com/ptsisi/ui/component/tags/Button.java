package com.ptsisi.ui.component.tags;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public class Button extends UIBean {

  String value;

  public Button(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    if (null != value) {
      value = getText(value);
    }
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
