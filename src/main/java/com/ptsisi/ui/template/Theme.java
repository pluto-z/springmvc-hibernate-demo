package com.ptsisi.ui.template;

import org.apache.commons.lang3.StringUtils;

public class Theme {

  private final String name;

  public Theme(String name) {
    super();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getTemplatePath(Class<?> clazz, String suffix) {
    StringBuilder sb = new StringBuilder(20);
    sb.append("/template/").append(name).append('/').append(StringUtils.uncapitalize(clazz.getSimpleName()))
        .append(suffix);
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    return name.equals(obj.toString());
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

}
