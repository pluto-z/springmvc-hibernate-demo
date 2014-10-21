package com.ptsisi.view.template;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zhaoding on 14-10-21.
 */
public class HeadTemplate implements TemplateDirectiveModel {

	@Override public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		if (!params.isEmpty()) {
			throw new TemplateModelException("this directive doesn't allow parameters");
		}
		if (loopVars.length != 0) {
			throw new TemplateModelException("this directive doesn't allow variables");
		}
		if (body != null) {
			env.getOut().write("aaaab");
		} else {
			throw new RuntimeException("missing body");
		}
	}
}
