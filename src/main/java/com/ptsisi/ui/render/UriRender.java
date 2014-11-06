package com.ptsisi.ui.render;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public class UriRender {

  private final String context;

  private final String suffix;
  // encode
  private boolean escapeAmp;

  public UriRender(String context, String suffix) {
    if (StringUtils.isNotBlank(suffix)) {
      if (suffix.charAt(0) != '.') {
        this.suffix = "." + suffix;
      } else {
        this.suffix = suffix;
      }
    } else {
      this.suffix = "";
    }
    if (StringUtils.isEmpty(context) || context.equals("/")) {
      this.context = "";
    } else {
      if (context.endsWith("/")) context = context.substring(0, context.length() - 1);
      if (!context.startsWith("/")) context = "/" + context;
      this.context = context;
    }
  }

  public String render(String referer, String uri, Map<String, String> params) {
    String separator = "&";
    if (escapeAmp) {
      separator = "&amp;";
    }
    StringBuilder sb = renderUri(referer, uri);
    sb.append(separator);
    for (String key : params.keySet()) {
      try {
        sb.append(key).append('=').append(URLEncoder.encode(params.get(key), "UTF-8"));
        sb.append(separator);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    sb.delete(sb.length() - separator.length(), sb.length());
    return sb.toString();
  }

  public String render(String referer, String uri, String... params) {
    String separator = "&";
    if (escapeAmp) {
      separator = "&amp;";
    }
    StringBuilder sb = renderUri(referer, uri);
    sb.append(separator);
    for (String param : params) {
      try {
        sb.append(URLEncoder.encode(param, "UTF-8"));
        sb.append(separator);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    sb.delete(sb.length() - separator.length(), sb.length());
    return sb.toString();
  }

  public String render(String referer, String uri) {
    return renderUri(referer, uri).toString();
  }

  private StringBuilder renderUri(String referer, String uri) {
    Assert.notNull(referer);
    StringBuilder sb = new StringBuilder();
    if (StringUtils.isEmpty(uri)) {
      sb.append(referer);
      return sb;
    }
    // query string
    String queryStr = null;
    int questIndex = uri.indexOf('?');
    if (-1 == questIndex) {
      questIndex = uri.length();
    } else {
      queryStr = uri.substring(questIndex + 1);
      uri = uri.substring(0, questIndex);
    }
    // uri
    if (uri.startsWith("/")) {
      sb.append(context);
      sb.append(uri.substring(0, questIndex));
    } else {
      int lastslash = referer.lastIndexOf("/");
      String namespace = referer.substring(0, lastslash);
      sb.append(namespace);
      if (uri.startsWith("!")) {
        int dot = referer.indexOf("!", lastslash);
        if (-1 == dot) {
          dot = referer.indexOf(".", lastslash);
        }
        dot = (-1 == dot) ? referer.length() : dot;
        String action = referer.substring(lastslash, dot);
        sb.append(action);
        sb.append(uri);
      } else {
        sb.append('/').append(uri);
      }
    }
    // prefix
    if (null != suffix) sb.append(suffix);
    if (null != queryStr) {
      sb.append('?').append(queryStr);
    }
    return sb;
  }

  public String getSuffix() {
    return suffix;
  }

  public boolean isEscapeAmp() {
    return escapeAmp;
  }

  public void setEscapeAmp(boolean escapeAmp) {
    this.escapeAmp = escapeAmp;
  }
}
