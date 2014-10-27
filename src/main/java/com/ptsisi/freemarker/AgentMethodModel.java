package com.ptsisi.freemarker;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ptsisi.freemarker.model.Agent;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * Created by zhaoding on 14-10-22.
 */
public class AgentMethodModel implements TemplateMethodModelEx {

	@Autowired
	private HttpServletRequest request;

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		return new Agent(request);
	}
}
