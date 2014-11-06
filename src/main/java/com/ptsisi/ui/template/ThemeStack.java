package com.ptsisi.ui.template;

import java.util.Stack;

public class ThemeStack {

  private Stack<Theme> themes = new Stack<Theme>();

  public Theme push(Theme item) {
    return themes.push(item);
  }

  public Theme pop() {
    return themes.pop();
  }

  public Theme peek() {
    return themes.peek();
  }

  public boolean isEmpty() {
    return themes.isEmpty();
  }

}
