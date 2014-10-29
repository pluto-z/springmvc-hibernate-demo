package com.ptsisi.daily.web.controller.common;

import com.ptsisi.common.exception.ServiceException;
import com.ptsisi.security.UnautherizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

/**
 * Created by zhaoding on 14-10-27.
 */
@Controller
@ControllerAdvice
public class ExceptionController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@RequestMapping("/unauthorized")
	@ExceptionHandler(UnautherizedException.class)
	public String unauthorized(UnautherizedException ue) {
		if (ue == null) {
			return "exception/unauthorized";
		} else {
			return "redirect:/login";
		}
	}

	@ExceptionHandler(ServiceException.class)
	public String serviceException(ServiceException se) {
		LOGGER.error("系统发生业务异常", se);
		RequestContextHolder.getRequestAttributes().setAttribute("exception", se, RequestAttributes.SCOPE_REQUEST);
		return "exception/service-exception";
	}

	@ExceptionHandler
	public String globalException(Throwable throwable) {
		if (throwable.getCause() instanceof BindException) {
			BindException bindException = (BindException) throwable.getCause();
			List<ObjectError> errors = bindException.getAllErrors();
			RequestContextHolder.getRequestAttributes().setAttribute("errors", errors, RequestAttributes.SCOPE_REQUEST);
			return "exception/bind-exception";
		}
		LOGGER.error("系统发生业务异常", throwable);
		return "exception/global-exception";
	}
}
