package com.ptsisi.ui.template;

import com.google.common.collect.Maps;

import java.util.Map;

public class Themes {

	public static final Theme Default = new Theme("html");

	public static final Map<String, Theme> themes = loadThemeProps();

	private static Map<String, Theme> loadThemeProps() {
		Map<String, Theme> themes = Maps.newHashMap();
		themes.put("html", Default);
		themes.put("form", new Theme("form"));
		return themes;
	}

}
