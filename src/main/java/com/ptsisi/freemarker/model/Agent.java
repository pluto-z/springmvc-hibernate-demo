package com.ptsisi.freemarker.model;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhaoding on 14-10-22.
 */
public class Agent {

	private final String browser;

	private final String version;

	public Agent(HttpServletRequest request) {
		Browser browser = Browser.parse(request.getHeader("USER-AGENT"));
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
