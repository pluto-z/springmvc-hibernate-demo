package com.ptsisi.ui.template;

import java.io.Writer;

import com.ptsisi.ui.component.Component;

public interface TemplateEngine {

  void render(String template, Writer writer, Component component) throws Exception;

  String getSuffix();
}
