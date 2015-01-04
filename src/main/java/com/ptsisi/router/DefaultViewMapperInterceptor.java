package com.ptsisi.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.ptsisi.daily.Menu;
import com.ptsisi.daily.Resource;
import com.ptsisi.daily.model.CustomPrincipal;
import com.ptsisi.daily.web.service.SecurityService;
import com.ptsisi.security.UnautherizedException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultViewMapperInterceptor implements HandlerInterceptor {

	final static char separator = '/';

	final static String POSTFIX = "Controller";

	final static String DAILY_REDIRECT = "daily_redirect";

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
				boolean need_container =
						request.getHeader("x-requested-with") == null && request.getParameter("x-requested-with") == null;
				if (need_container) {
					modelAndView.addObject("default_menus", menus);
					modelAndView.addObject("default_user", principal.getUser());
				}
			} catch (UnautherizedException e) {
				modelAndView.addObject("login", true);
			}
			reMapView((HandlerMethod) handler, modelAndView);
			if (modelAndView.getViewName().startsWith("redirect:")) {
				modelAndView.addObject(DAILY_REDIRECT, 1);
			}
			if (null != request.getParameter(DAILY_REDIRECT)) {
				StringBuffer requestUrl = request.getRequestURL();
				String queryString = request.getQueryString();
				if (StringUtils.isNotBlank(queryString)) {
					String[] queryStrings = StringUtils.split(queryString, '&');
					if (ArrayUtils.isNotEmpty(queryStrings)) {
						Map<String, String> params = Maps.newHashMap();
						for (String param : queryStrings) {
							String[] paramArray = StringUtils.split(param, '=');
							if (!"x-requested-with".equals(paramArray[0]) && !DAILY_REDIRECT.equals(paramArray[0])) {
								params.put(paramArray[0], paramArray[1]);
							}
						}
						if (!params.isEmpty()) {
							response.setHeader("params", new ObjectMapper().writeValueAsString(params));
						}
					}
				}
				response.setHeader(DAILY_REDIRECT, requestUrl.toString());
			}
		}
	}

	private void reMapView(HandlerMethod handlerMethod, ModelAndView modelAndView) {
		String className = handlerMethod.getBeanType().getName();
		if (StringUtils.uncapitalize(Void.class.getSimpleName())
				.equals(handlerMethod.getMethod().getReturnType().getName())) {
			modelAndView.setViewName(handlerMethod.getMethod().getName());
		}
		String viewName = modelAndView.getViewName();
		if (viewName == null || viewName.startsWith("redirect:") || viewName.startsWith("forward:")) return;
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
