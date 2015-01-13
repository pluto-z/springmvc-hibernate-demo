package com.ptsisi.daily.web.handler;

import com.ptsisi.common.exception.ServiceException;
import com.ptsisi.security.UnautherizedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zhaoding on 14-11-3.
 */
@Component(value = "exceptionResolver")
public class DefaultExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView modelAndView;
		if (ex instanceof UnautherizedException) {
			modelAndView = new ModelAndView("exception/unauthorized");
		} else if (ex instanceof ServiceException) {
			modelAndView = new ModelAndView("exception/service-exception");
		} else if (ex instanceof UnauthorizedException) {
			modelAndView = new ModelAndView("exception/unauthorized");
		} else if (ex instanceof BindException) {
			List<ObjectError> errors = ((BindException) ex).getAllErrors();
			RequestContextHolder.getRequestAttributes().setAttribute("errors", errors, RequestAttributes.SCOPE_REQUEST);
			modelAndView = new ModelAndView("exception/bind-exception");
		} else {
			modelAndView = new ModelAndView("exception/global-exception");
		}
		modelAndView.addObject("ex_head",
				request.getHeader("x-requested-with") == null && request.getParameter("x-requested-with") == null);
		return modelAndView;
	}
}
