package com.ptsisi.view.tag;

import com.ptsisi.view.tag.freemarker.TagModel;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoding on 14-10-21.
 */
public abstract class AbstractTagModels {

	protected final Map<Class<?>, TagModel> models = new HashMap<Class<?>, TagModel>();

	protected AbstractTagModels(ServletContext servletContext) {

	}
}
