package com.ptsisi.ui.component;

import java.util.Iterator;
import java.util.Stack;

import com.ptsisi.ui.generate.UIIdGenerator;
import com.ptsisi.ui.render.ActionUriRender;
import com.ptsisi.ui.template.TemplateEngine;
import com.ptsisi.ui.template.Theme;
import com.ptsisi.ui.template.ThemeStack;
import com.ptsisi.ui.template.Themes;

public class ComponentContext {

  private ActionUriRender render;

  private UIIdGenerator idGenerator;

  private TemplateEngine engine;

  private ThemeStack themeStack = new ThemeStack();

  private Stack<Component> components = new Stack<Component>();

  public ComponentContext(UIIdGenerator idGenerator, TemplateEngine engine, ActionUriRender render) {
    this.idGenerator = idGenerator;
    this.engine = engine;
    this.render = render;
  }

  @SuppressWarnings("unchecked")
  public <T extends Component> T find(Class<T> clazz) {
    for (Iterator<Component> it = components.iterator(); it.hasNext();) {
      Component component = it.next();
      if (component.getClass().isAssignableFrom(clazz)) return (T) component;
    }
    return null;
  }

  public Component pop() {
    Component elem = components.pop();
    if (null != elem.getTheme()) {
      themeStack.pop();
    }
    return elem;
  }

  public Theme theme() {
    return themeStack.isEmpty() ? Themes.Default : themeStack.peek();
  }

  public void push(Component component) {
    components.push(component);
    if (null != component.getTheme()) themeStack.push(Themes.themes.get(component.getTheme()));
  }

  public TemplateEngine getEngine() {
    return engine;
  }

  public UIIdGenerator getIdGenerator() {
    return idGenerator;
  }

  public ActionUriRender getRender() {
    return render;
  }

  public ThemeStack getThemeStack() {
    return themeStack;
  }

  public Stack<Component> getComponents() {
    return components;
  }

}
