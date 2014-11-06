package com.ptsisi.ui.component.tags;

import org.apache.commons.lang3.StringUtils;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

public class Div extends UIBean {

  private String href;

  private String astarget;

  public Div(ComponentContext context) {
    super(context);
  }

  @Override
  protected void evaluateParams() {
    if (null == astarget && (null != id || null != href)) astarget = "true";
    if (null != href) {
      generateIdIfEmpty();
      href = render(this.href);
    }
    if ("false".equals(astarget)) {
      String className = "ajax_container";
      if (null != parameters.get("class")) {
        className = StringUtils.join(className, " ", parameters.get("class").toString());
      }
      parameters.put("class", className);
    }
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getAstarget() {
    return astarget;
  }

  public void setAstarget(String astarget) {
    this.astarget = astarget;
  }

}
