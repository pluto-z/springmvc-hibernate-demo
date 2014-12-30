package com.ptsisi.ui.component.tags;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public class Checkbox extends UIBean {

  public Checkbox(ComponentContext context) {
    super(context);
  }

  protected String name;
  protected String label;
  protected String title;
  protected Object value = "";
  protected boolean checked = false;
  protected String required;

  @Override
  protected void evaluateParams() {
    if (null == this.id) generateIdIfEmpty();
    label = processLabel(label);
    title = null != title ? getText(title) : label;

    Form myform = findAncestor(Form.class);
    if (null != myform) {
      if ("true".equals(required)) {
        myform.addCheck(id + "_span", "assert($(\"#" + id + ":checked\").length != 0,'必须勾选一项')");
      }
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
  }
}
