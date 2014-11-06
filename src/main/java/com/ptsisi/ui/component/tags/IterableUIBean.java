package com.ptsisi.ui.component.tags;

import java.io.Writer;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public abstract class IterableUIBean extends UIBean {

  public IterableUIBean(ComponentContext context) {
    super(context);
  }

  protected abstract boolean next();

  protected void iterator(Writer writer, String body) {
    this.body = body;
    try {
      mergeTemplate(writer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean doStart(Writer writer) {
    evaluateParams();
    return next();
  }

  @Override
  public boolean doEnd(Writer writer, String body) {
    iterator(writer, body);
    return next();
  }
}
