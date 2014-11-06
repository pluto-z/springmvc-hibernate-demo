package com.ptsisi.ui.component.tags;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public class Radio extends UIBean {

  protected String name;
  protected String label;
  protected String title;
  protected Object value = "";

  public Radio(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    if (null == this.id) generateIdIfEmpty();
    label = processLabel(label);
    title = null != title ? getText(title) : label;
    this.value = booleanize(value);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

}
