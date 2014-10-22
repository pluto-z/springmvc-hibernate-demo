package com.ptsisi.freemarker;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhaoding on 14-10-22.
 */
public class RequestProviderMothodModel implements TemplateMethodModelEx {

	@Autowired
	private HttpServletRequest request;

	@Override public Object exec(List arguments) throws TemplateModelException {
		return request;
	}
}
