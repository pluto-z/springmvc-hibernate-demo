package com.ptsisi.ui.template;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.google.common.base.Throwables;
import com.ptsisi.ui.component.Component;

import freemarker.cache.StrongCacheStorage;
import freemarker.cache.TemplateLoader;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.AllHttpScopesHashModel;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@org.springframework.stereotype.Component
public class FreeMarkerTemplateEngine implements TemplateEngine {

  private static final String ATTR_TEMPLATE_MODEL = ".freemarker.TemplateModel";

  private static final String TAG_PROPERTIES_FILE_NAME = "tag.properties";

  private Logger logger = LoggerFactory.getLogger(getClass());

  private Configuration config;

  @javax.annotation.Resource
  private HttpServletRequest request;

  @javax.annotation.Resource
  private TemplateLoader templateLoader;

  private Resource processLocation() {
    return new ClassPathResource(TAG_PROPERTIES_FILE_NAME, getClass());
  }

  @PostConstruct
  public void init() throws IOException, TemplateException {
    if (config == null) config = new Configuration();
    Properties props = new Properties();
    PropertiesLoaderUtils.fillProperties(props, processLocation());
    if (!props.isEmpty()) {
      config.setSettings(props);
    }
    config.setObjectWrapper(DefaultObjectWrapper.getDefaultInstance());
    config.setTemplateLoader(templateLoader);
    config.setCacheStorage(new StrongCacheStorage());
    config.setAutoImports(new HashMap<String, Object>(0));
    config.setAutoIncludes(new ArrayList<String>(0));
  }

  @Override
  public void render(String template, Writer writer, Component component) throws Exception {
    SimpleHash model = buildModel(component);
    Object prevTag = model.get("tag");
    model.put("tag", component);
    getTemplate(template).process(model, writer);
    if (null != prevTag) model.put("tag", prevTag);
  }

  @Override
  public String getSuffix() {
    return ".ftl";
  }

  private Template getTemplate(String templateName) throws IOException {
    try {
      return config.getTemplate(templateName, "UTF-8");
    } catch (IOException e) {
      logger.error("Couldn't load template '{}',loader is {}", templateName, config.getTemplateLoader().getClass());
      throw Throwables.propagate(e);
    }
  }

  private ServletContext getServletContext() {
    return request.getSession().getServletContext();
  }

  private SimpleHash buildModel(Component component) {
    SimpleHash model = (SimpleHash) request.getAttribute(ATTR_TEMPLATE_MODEL);
    if (null == model) {
      model = new AllHttpScopesHashModel(config.getObjectWrapper(), getServletContext(), request);
      model.put(FreemarkerServlet.KEY_JSP_TAGLIBS, new TaglibFactory(getServletContext()));
      model.put(FreemarkerServlet.KEY_REQUEST_PARAMETERS, new HttpRequestParametersHashModel(request));
      model.put("request", request);
      model.put("base", request.getContextPath());
      request.setAttribute(ATTR_TEMPLATE_MODEL, model);
    }
    return model;
  }
}
