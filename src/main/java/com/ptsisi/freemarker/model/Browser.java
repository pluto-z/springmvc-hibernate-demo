package com.ptsisi.freemarker.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoding on 14-10-22.
 */
public class Browser implements Serializable, Comparable<Browser> {

	public static Map<String, Browser> browsers = new HashMap<String, Browser>();

	public static final Browser UNKNOWN = new Browser(BrowserCategory.Unknown, null);

	public final BrowserCategory category;
	public final String version;

	public Browser(BrowserCategory category, String version) {
		super();
		this.category = category;
		this.version = version;
	}

	/**
	 * Iterates over all Browsers to compare the browser signature with the user
	 * agent string. If no match can be found Browser.UNKNOWN will be returned.
	 *
	 * @param agentString
	 * @return Browser
	 */
	public static Browser parse(final String agentString) {
		if (StringUtils.isEmpty(agentString)) {
			return Browser.UNKNOWN;
		}
		// first consider engine
		for (Engine engine : Engine.values()) {
			String egineName = engine.name;
			if (agentString.contains(egineName)) {
				for (BrowserCategory category : engine.browserCategories) {
					String version = category.match(agentString);
					if (version != null) {
						String key = category.getName() + "/" + version;
						Browser browser = browsers.get(key);
						if (null == browser) {
							browser = new Browser(category, version);
							browsers.put(key, browser);
						}
						return browser;
					}
				}
			}
		}
		// for some usagent without suitable enginename;
		for (BrowserCategory category : BrowserCategory.values()) {
			String version = category.match(agentString);
			if (version != null) {
				String key = category.getName() + "/" + version;
				Browser browser = browsers.get(key);
				if (null == browser) {
					browser = new Browser(category, version);
					browsers.put(key, browser);
				}
				return browser;
			}
		}
		return Browser.UNKNOWN;
	}

	@Override
	public String toString() {
		return category.getName() + " " + (version == null ? "" : version);
	}

	public int compareTo(Browser o) {
		return category.compareTo(o.category);
	}
}
