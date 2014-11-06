package com.ptsisi.ui.component.tags;

import java.io.Writer;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public class Validity extends UIBean {

  public Validity(ComponentContext context) {
    super(context);
  }

  @Override
  public boolean doEnd(Writer writer, String body) {
    Form myform = findAncestor(Form.class);
    if (null != myform) myform.addCheck(body);
    return false;
  }
}
