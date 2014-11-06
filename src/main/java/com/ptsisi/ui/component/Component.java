package com.ptsisi.ui.component;

import java.io.Writer;
import java.util.Map;

import com.google.common.collect.Maps;

public abstract class Component {

  protected Map<String, Object> parameters = Maps.newLinkedHashMap();

  private String theme;

  protected final ComponentContext context;

  public Component(ComponentContext context) {
    this.context = context;
  }

  public final boolean start(Writer writer) {
    this.context.push(this);
    return doStart(writer);
  }

  public final boolean end(Writer writer, String body) {
    this.context.pop();
    return doEnd(writer, body);
  }

  public abstract boolean doStart(Writer writer);

  public abstract boolean doEnd(Writer writer, String body);

  protected <T extends Component> T findAncestor(Class<T> clazz) {
    return context.find(clazz);
  }

  public final String getParameterString() {
    StringBuilder sb = new StringBuilder(parameters.size() * 10);
    for (Map.Entry<String, Object> parameter : this.parameters.entrySet()) {
      sb.append(" ").append(parameter.getKey()).append("=\"").append(parameter.getValue().toString()).append("\"");
    }
    return sb.toString();
  }

  protected abstract void evaluateParams();

  public void addParameter(String key, Object value) {
    if (key != null) {
      if (value == null) parameters.remove(key);
      else parameters.put(key, value);
    }
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public boolean usesBody() {
    return false;
  }

  public ComponentContext getContext() {
    return context;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }

}
