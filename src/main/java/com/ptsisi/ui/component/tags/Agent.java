package com.ptsisi.ui.component.tags;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;
import com.ptsisi.ui.http.Browser;

/**
 * Created by zhaoding on 14-10-22.
 */
public class Agent extends UIBean {

  private final String browser;

  private final String version;

  public Agent(ComponentContext context) {
    super(context);
    Browser browser = Browser.parse(getRequest().getHeader("USER-AGENT"));
    this.browser = browser.category.getName();
    this.version = browser.version;
  }

  public String getBrowser() {
    return browser;
  }

  public String getVersion() {
    return version;
  }
}
