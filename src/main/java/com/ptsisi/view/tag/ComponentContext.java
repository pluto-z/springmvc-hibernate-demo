package com.ptsisi.view.tag;

import java.util.Stack;

/**
 * Created by zhaoding on 14-10-21.
 */
public class ComponentContext {

	private ThemeStack themeStack = new ThemeStack();

	private Stack<Component> components = new Stack<Component>();

	public <T extends  Component> T find(Class<T>clazz) {
		components.find { component => clazz == component.getClass } match {
			case Some(c) => c.asInstanceOf[T]
			case None => null.asInstanceOf[T]
		}
	}

	def pop(): Component = {
		val elem = components.pop()
		if (null != elem.theme) themeStack.pop()
		elem
	}

	def theme: Theme = {
		if (themeStack.isEmpty) Themes.Default
		else themeStack.peek
	}

	def push(component: Component): Unit = {
		components.push(component)
		if (null != component.theme) themeStack.push(Themes(component.theme))
	}

}
