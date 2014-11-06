package com.ptsisi.ui.component.tags;

import org.apache.commons.lang3.StringUtils;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public class Select2 extends UIBean {

  private String label;
  private String required;
  private String keyName = "id", valueName = "name";
  private String name1st, name2nd;
  private Object items1st, items2nd;
  private String size = "10";

  private String style = "width:250px;height:200px";

  private String option;

  public Select2(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    if (null != label) label = getText(label);
    generateIdIfEmpty();
    Form myform = findAncestor(Form.class);
    if (null != myform) {
      String mySelectId = id + "_1";
      if ("true".equals(required)) {
        myform.addCheck(mySelectId, "assert(bg.select.selectAll,'requireSelect')");
      } else {
        myform.addCheck(mySelectId,
            StringUtils.join("assert(bg.select.selectAll(document.getElementById('", mySelectId, "'))||true)"));
      }
    }

  }

  public String getName1st() {
    return name1st;
  }

  public void setName1st(String name1st) {
    this.name1st = name1st;
  }

  public String getName2nd() {
    return name2nd;
  }

  public void setName2nd(String name2nd) {
    this.name2nd = name2nd;
  }

  public Object getItems1st() {
    return items1st;
  }

  public void setItems1st(Object items1st) {
    this.items1st = items1st;
  }

  public Object getItems2nd() {
    return items2nd;
  }

  public void setItems2nd(Object items2nd) {
    this.items2nd = items2nd;
  }

  public String getKeyName() {
    return keyName;
  }

  public void setKeyName(String keyName) {
    this.keyName = keyName;
  }

  public String getValueName() {
    return valueName;
  }

  public void setValueName(String valueName) {
    this.valueName = valueName;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public void setOption(String option) {
    if (StringUtils.isNotBlank(option)) {
      if (StringUtils.contains(option, "$")) {
        this.option = option;
      } else if (StringUtils.contains(option, ",")) {
        keyName = StringUtils.substringBefore(option, ",");
        valueName = StringUtils.substringAfter(option, ",");
      }
    }
  }

  public String getOption() {
    return option;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }
}
