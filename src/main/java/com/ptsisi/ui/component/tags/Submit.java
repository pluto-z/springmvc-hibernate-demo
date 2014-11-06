package com.ptsisi.ui.component.tags;

import org.apache.commons.lang3.StringUtils;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public class Submit extends UIBean {

  String formId;
  String onsubmit;
  String action;
  String value;
  String target;

  public Submit(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    if (null == formId) {
      Form f = findAncestor(Form.class);
      if (null != f) formId = f.getId();
    }
    if (null != onsubmit && -1 != onsubmit.indexOf('(')) {
      onsubmit = StringUtils.join("'", onsubmit, "'");
    }
    if (null != value) {
      value = getText(value);
    }
    if (null != action) {
      action = render(action);
    }
  }

  public String getFormId() {
    return formId;
  }

  public void setFormId(String formId) {
    this.formId = formId;
  }

  public String getOnsubmit() {
    return onsubmit;
  }

  public void setOnsubmit(String onsubmit) {
    this.onsubmit = onsubmit;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String href) {
    this.action = href;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }
}
