package com.ptsisi.router;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ptsisi.daily.Menu;
import com.ptsisi.daily.Resource;
import com.ptsisi.daily.model.CustomPrincipal;
import com.ptsisi.daily.web.service.SecurityService;
import com.ptsisi.security.UnautherizedException;

public class DefaultViewMapperInterceptor implements HandlerInterceptor {

  final static char separator = '/';

  final static String POSTFIX = "Controller";

  @javax.annotation.Resource
  private SecurityService securityService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    if (modelAndView != null) {
      try {
        CustomPrincipal principal = CustomPrincipal.getCurrentPrincipal();
        Set<Resource> resources = principal.getUser().getResources();
        List<Menu> menus = securityService.getMenus(resources);
        modelAndView.addObject("menus", menus);
        modelAndView.addObject("user", principal.getUser());
        modelAndView.addObject("needContainer",
            request.getHeader("x-requested-with") == null && request.getParameter("x-requested-with") == null);
      } catch (UnautherizedException e) {

      }
      reMapView((HandlerMethod) handler, modelAndView);
    }
  }

  private void reMapView(HandlerMethod handlerMethod, ModelAndView modelAndView) {
    String className = handlerMethod.getBeanType().getName();
    if (StringUtils.uncapitalize(Void.class.getSimpleName())
        .equals(handlerMethod.getMethod().getReturnType().getName())) {
      modelAndView.setViewName(handlerMethod.getMethod().getName());
    }
    String viewName = modelAndView.getViewName();
    if (viewName == null || viewName.startsWith("redirect") || viewName.startsWith("forward:")) return;
    StringBuilder buf = new StringBuilder();
    buf.append(separator);
    buf.append(getFullPath(className));
    buf.append(separator);
    buf.append(viewName);
    modelAndView.setViewName(buf.toString());
  }

  private String getFullPath(String className) {
    String simpleName = className.substring(className.lastIndexOf('.') + 1);
    if (StringUtils.contains(simpleName, POSTFIX)) {
      simpleName = StringUtils.uncapitalize(simpleName.substring(0, simpleName.length() - POSTFIX.length()));
    } else {
      simpleName = StringUtils.uncapitalize(simpleName);
    }

    StringBuilder infix = new StringBuilder();
    infix.append(StringUtils.substringBeforeLast(className, "."));
    if (infix.length() == 0) return simpleName;
    infix.append('.');
    infix.append(simpleName);
    // 将.替换成/
    for (int i = 0; i < infix.length(); i++) {
      if (infix.charAt(i) == '.') {
        infix.setCharAt(i, '/');
      }
    }
    return infix.toString();
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {

  }

}
