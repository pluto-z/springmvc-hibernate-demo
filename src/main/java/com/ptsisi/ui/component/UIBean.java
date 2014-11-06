package com.ptsisi.ui.component;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Maps;
import com.ptsisi.ui.template.TemplateEngine;

public class UIBean extends Component {

  protected String id;

  private String cssClass;

  protected String body;

  protected static final Map<Object, String> Booleans = Maps.newHashMap();
  static {
    Booleans.put(Boolean.TRUE, "1");
    Booleans.put(Boolean.FALSE, "0");
    Booleans.put("y", "1");
    Booleans.put("n", "0");
    Booleans.put("Y", "1");
    Booleans.put("N", "0");
    Booleans.put("true", "1");
    Booleans.put("false", "0");
    Booleans.put("是", "1");
    Booleans.put("否", "0");
    Booleans.put("1", "1");
    Booleans.put("0", "0");
    Booleans.put(1, "1");
    Booleans.put(0, "0");
  }

  public UIBean(ComponentContext context) {
    super(context);
  }

  @Override
  public boolean doStart(Writer writer) {
    evaluateParams();
    return true;
  }

  protected final void mergeTemplate(Writer writer) throws Exception {
    TemplateEngine engine = context.getEngine();
    engine.render(context.theme().getTemplatePath(getClass(), engine.getSuffix()), writer, this);
  }

  @Override
  public boolean doEnd(Writer writer, String body) {
    this.body = body;
    try {
      mergeTemplate(writer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return false;
  }

  protected String render(String uri) {
    return context.getRender().render(getRequest(), uri);
  }

  @Override
  protected void evaluateParams() {
  }

  protected HttpServletRequest getRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }

  protected String getRequestURI() {
    return getRequest().getRequestURI();
  }

  protected String getRequestParameter(String name) {
    return getRequest().getParameter(name);
  }

  protected String getText(String text) {
    return getText(text, text);
  }

  protected String getText(String text, String defaultText) {
    if (StringUtils.isEmpty(text)) return defaultText;
    if (!CharUtils.isAsciiAlpha(text.charAt(0))) return defaultText;
    if (-1 == text.indexOf('.') || -1 < text.indexOf(' ')) return defaultText;
    else {
      return text;
    }
  }

  protected Object getValue(Object obj, String property) {
    if (obj == null) {
      return null;
    } else if (obj instanceof Map) {
      return ((Map<?, ?>) obj).get(property);
    } else {
      try {
        return PropertyUtils.getProperty(obj, property);
      } catch (Exception e) {
        return null;
      }
    }
  }

  protected void generateIdIfEmpty() {
    if (StringUtils.isEmpty(id)) {
      id = context.getIdGenerator().generate(getClass());
    }
  }

  protected String processLabel(String label) {
    return StringUtils.isBlank(label) ? null : getText(label);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCssClass() {
    return cssClass;
  }

  public void setCssClass(String cssClass) {
    this.cssClass = cssClass;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Object booleanize(Object obj) {
    Object booleanValue = Booleans.get(obj);
    return null == booleanValue ? obj : booleanValue;
  }

  public boolean getBooleanValue(Object obj) {
    String booleanValue = Booleans.get(obj);
    return null == booleanValue ? false : "1".equals(booleanValue);
  }

  public String getBody() {
    return body;
  }

}
