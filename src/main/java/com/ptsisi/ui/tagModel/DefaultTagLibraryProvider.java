package com.ptsisi.ui.tagModel;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.generate.IndexableIdGenerator;
import com.ptsisi.ui.generate.UIIdGenerator;
import com.ptsisi.ui.render.ActionUriRender;
import com.ptsisi.ui.template.TemplateEngine;

@Component("defaultTagLibraryProvider")
public class DefaultTagLibraryProvider implements TagLibraryProvider {

  @Resource
  private TemplateEngine engine;

  @Resource
  private ActionUriRender render;

  public TagLibrary getTagLibrary(HttpServletRequest request) {
    String queryString = request.getQueryString();
    String fullpath = null == queryString ? request.getRequestURI() : (request.getRequestURI() + queryString);
    UIIdGenerator idGenerator = new IndexableIdGenerator(String.valueOf(Math.abs(fullpath.hashCode())));
    ComponentContext context = new ComponentContext(idGenerator, engine, render);
    return new TagLibrary(context);
  }

}
