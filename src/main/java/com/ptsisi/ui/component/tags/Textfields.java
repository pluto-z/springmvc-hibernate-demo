package com.ptsisi.ui.component.tags;

import org.apache.commons.lang3.StringUtils;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public class Textfields extends UIBean {

  private String names;

  private Textfield[] fields;

  public Textfields(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    String[] nameArray = StringUtils.split(names, ',');
    fields = new Textfield[nameArray.length];
    for (int i = 0; i < nameArray.length; i++) {
      fields[i] = new Textfield(context);
      String name = nameArray[i];
      String title = name;
      int semiconIndex = name.indexOf(';');
      if (-1 != semiconIndex) {
        title = name.substring(semiconIndex + 1);
        name = name.substring(0, semiconIndex);
      }
      fields[i].setName(name);
      fields[i].setLabel(title);
      fields[i].evaluateParams();
    }
  }

  public void setNames(String names) {
    this.names = names;
  }

  public Textfield[] getFields() {
    return fields;
  }

}
