package com.ptsisi.freemarker;

import com.ptsisi.freemarker.model.Agent;
import com.ptsisi.freemarker.model.Browser;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhaoding on 14-10-22.
 */
public class AgentMethodModel implements TemplateMethodModelEx {

	@Autowired
	private HttpServletRequest request;

	@Override public Object exec(List arguments) throws TemplateModelException {
		return new Agent(request);
	}
}
