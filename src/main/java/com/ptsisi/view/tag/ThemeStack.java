package com.ptsisi.view.tag;

import java.util.Stack;

/**
 * Created by zhaoding on 14-10-21.
 */
public class ThemeStack {
	private static final Stack<Theme> themes = new Stack<Theme>();

	public static Theme push(Theme item) {
		return themes.push(item);
	}

	public static Theme pop() {
		return themes.pop();
	}

	public static Theme peek() {
		return themes.peek();
	}

	public static boolean isEmpty() {
		return themes.isEmpty();
	}
}
