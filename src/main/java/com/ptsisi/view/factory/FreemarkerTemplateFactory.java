package com.ptsisi.view.factory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoding on 14-10-21.
 */
public class FreemarkerTemplateFactory {

	private final static Logger log = LoggerFactory.getLogger(FreemarkerTemplateFactory.class);

	private final static String TEMPLATE_PATH = "/config/template";

	private final static String ENCODING = "utf-8";

	private final static String SUFFIX = ".ftl";

	private static Configuration conf = new Configuration();
	static {
		// 设置模板文件路径
		conf.setClassForTemplateLoading(FreemarkerTemplateFactory.class, TEMPLATE_PATH);
		conf.setDefaultEncoding(ENCODING);
	}

	private static Map<String, Template> tempMap = new HashMap<String, Template>();

}
