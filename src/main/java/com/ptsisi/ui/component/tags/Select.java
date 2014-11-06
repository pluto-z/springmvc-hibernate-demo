package com.ptsisi.ui.component.tags;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public class Select extends UIBean {

  protected String name;

  private Object items = Collections.emptyList();
  private String empty;
  private Object value;

  private String keyName;
  private String valueName;

  private String label;
  protected String title;

  protected String comment;
  protected String check;
  protected String required;

  protected String option;

  public Select(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    if (null == keyName) {
      if (items instanceof Map<?, ?>) {
        keyName = "key";
        valueName = "value";
        items = ((Map<?, ?>) items).entrySet();
      } else {
        keyName = "id";
        valueName = "name";
      }
    }
    if (null == this.id) generateIdIfEmpty();
    label = processLabel(label);
    title = null != title ? getText(title) : label;
    Form myform = findAncestor(Form.class);
    if (null != myform) {
      if ("true".equals(required)) myform.addRequire(id);
      if (null != check) myform.addCheck(id, check);
    }
    if (null == value) value = getRequestParameter(name);
    // trim empty string to null for speed up isSelected
    if ((value instanceof String) && StringUtils.isEmpty((String) value)) value = null;
  }

  public boolean isSelected(Object obj) {
    if (null == value) return false;
    else try {
      Object nobj = obj;
      if (obj instanceof Map.Entry<?, ?>) nobj = ((Map.Entry<?, ?>) obj).getKey();
      boolean rs = value.equals(nobj) || value.equals(PropertyUtils.getProperty(nobj, keyName));
      return rs || value.toString().equals(nobj.toString())
          || value.toString().equals(String.valueOf(PropertyUtils.getProperty(nobj, keyName)));
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getItems() {
    return items;
  }

  public void setItems(Object items) {
    this.items = items;
  }

  public String getEmpty() {
    return empty;
  }

  public void setEmpty(String empty) {
    this.empty = empty;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public String getKeyName() {
    return keyName;
  }

  public String getValueName() {
    return valueName;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setOption(String option) {
    if (null != option) {
      if (StringUtils.contains(option, "$")) {
        this.option = option;
      } else if (StringUtils.contains(option, ",")) {
        keyName = StringUtils.substringBefore(option, ",");
        valueName = StringUtils.substringAfter(option, ",");
      }
    }
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

  public String getOption() {
    return option;
  }
}
