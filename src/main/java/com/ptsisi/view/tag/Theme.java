package com.ptsisi.view.tag;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhaoding on 14-10-21.
 */
public class Theme {

	public static final Theme DEFAULT = new Theme("html");

	public static final Map<String, Theme> themes = loadThemeProps();

	public String parent;

	private String name;

	public Theme(String name) {
		super();
		this.name = name;
	}

	private static Map<String, Theme> loadThemeProps() {
		Map<String, Theme> themePropMap = new HashMap<String, Theme>();
		URL url = ClassLoader.getSystemResource("template/html/theme.properties");
		String themeName = StringUtils.substringBetween(url.getPath(), "template/", "/theme.properties");
		Theme theme = new Theme(themeName);
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = url.openStream();
			properties.load(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String parent = properties.getProperty("parent");
		if (StringUtils.isNoneBlank(parent)) {
			theme.setParent(parent.trim());
		}
		themePropMap.put(themeName, theme);
		return themePropMap;
	}

	public String getTemplatePath(Class<?> clazz, String suffix) {
		return new StringBuilder(20).append("/template/").append(this.name).append("/")
				.append(StringUtils.uncapitalize(clazz.getSimpleName())).append(suffix).toString();
	}

	@Override public boolean equals(Object obj) {
		return this.name.equals(obj.toString());
	}

	@Override public int hashCode() {
		return this.name.hashCode();
	}

	@Override public String toString() {
		return this.name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
