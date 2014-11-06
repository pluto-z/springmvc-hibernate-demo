package com.ptsisi.ui.component.tags;

import java.text.SimpleDateFormat;
import java.util.Map;

import com.google.common.collect.Maps;
import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public class Date extends UIBean {

  public static final Map<String, String> ResvervedFormats = Maps.newHashMap();
  {
    ResvervedFormats.put("date", "yyyy-MM-dd");
    ResvervedFormats.put("datetime", "yyyy-MM-dd HH:mm:ss");
  }

  private String name;

  private String label;

  private String title;

  private String comment;

  private String check;

  private String required;

  private Object value = "";

  private String format = "date";

  protected String minDate;

  protected String maxDate;

  public Date(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    if (null == this.id) generateIdIfEmpty();
    label = processLabel(label);
    title = null != title ? getText(title) : label;
    Form myform = findAncestor(Form.class);
    if (null != myform) {
      if ("true".equals(required)) myform.addRequire(id);
      if (null != check) myform.addCheck(id, check);
    }
    String format2 = ResvervedFormats.get(format);
    if (null != format2) format = format2;
    if (value instanceof java.util.Date) {
      SimpleDateFormat dformat = new SimpleDateFormat(format);
      value = dformat.format((java.util.Date) value);
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

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCheck() {
    return check;
  }

  public void setCheck(String check) {
    this.check = check;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getMinDate() {
    return minDate;
  }

  public void setMinDate(String minDate) {
    this.minDate = minDate;
  }

  public String getMaxDate() {
    return maxDate;
  }

  public void setMaxDate(String maxDate) {
    this.maxDate = maxDate;
  }
}
