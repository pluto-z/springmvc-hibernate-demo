package com.ptsisi.router;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ptsisi.common.lang.ClassLoaders;

import freemarker.cache.URLTemplateLoader;
import freemarker.template.utility.StringUtil;

@Component("defaultTemplateLoader")
public class DefaultTemplateLoader extends URLTemplateLoader {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Resource
  private HttpServletRequest request;

  private String defaultParentPath;

  public DefaultTemplateLoader() {
    super();
  }

  @PostConstruct
  public void init() {
    this.defaultParentPath = "/pages/";
  }

  protected URL getURL(String name) {
    URL url = null;
    if (StringUtils.isNotEmpty(defaultParentPath)) {
      defaultParentPath = defaultParentPath.replace('\\', '/');
      if (!defaultParentPath.endsWith("/")) {
        defaultParentPath += "/";
      }
      if (!defaultParentPath.startsWith("/")) {
        defaultParentPath = "/" + defaultParentPath;
      }
      if (name.startsWith("/")) {
        name = name.substring(1);
      }
      String fullPath = defaultParentPath + name;
      try {
        url = request.getSession().getServletContext().getResource(fullPath);
      } catch (MalformedURLException e) {
        logger.warn("Could not retrieve resource " + StringUtil.jQuoteNoXSS(fullPath), e);
      }
    }
    return url == null ? ClassLoaders.getResource(name, getClass()) : url;
  }

}
