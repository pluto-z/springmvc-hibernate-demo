package com.ptsisi.ui.component.tags;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public class AbstractTextBean extends UIBean {

  protected String name;
  protected String label;
  protected String title;
  protected String comment;
  protected String required;
  protected Object value = "";
  protected String check;
  protected String maxlength = "100";

  public AbstractTextBean(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    generateIdIfEmpty();
    label = processLabel(label);
    title = null != title ? getText(title) : label;
    Form myform = findAncestor(Form.class);
    if (null != myform) {
      if ("true".equals(required)) myform.addRequire(id);
      if (null != check) myform.addCheck(id, check);
    }
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

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
  }

  public String getCheck() {
    return check;
  }

  public void setCheck(String check) {
    this.check = check;
  }

  public Object getValue() {
    return (String) value;
  }

  public void setValue(Object value) {
    this.value = String.valueOf(value);
  }

  public String getMaxlength() {
    return maxlength;
  }

  public void setMaxlength(String maxlength) {
    this.maxlength = maxlength;
  }
}
