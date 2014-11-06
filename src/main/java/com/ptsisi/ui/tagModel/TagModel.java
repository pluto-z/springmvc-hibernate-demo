package com.ptsisi.ui.tagModel;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ptsisi.ui.component.Component;
import com.ptsisi.ui.component.ComponentContext;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class TagModel implements TemplateDirectiveModel {

  private static final Logger logger = LoggerFactory.getLogger(TagModel.class);

  private ComponentContext context;

  private Constructor<? extends Component> componentCon;

  public TagModel(ComponentContext context, Class<? extends Component> clazz) {
    this.context = context;
    if (clazz != null) {
      try {
        componentCon = clazz.getConstructor(ComponentContext.class);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  protected Component getBean() {
    try {
      return componentCon.newInstance(context);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
      throws TemplateException, IOException {
    Component bean = getBean();
    BeansWrapper objectWrapper = BeansWrapper.getDefaultInstance();
    for (Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator(); iterator.hasNext();) {
      Map.Entry<String, Object> entry = iterator.next();
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value != null) {
        if (PropertyUtils.isWriteable(bean, key)) {
          if (value instanceof TemplateModel) {
            try {
              value = objectWrapper.unwrap((TemplateModel) value);
            } catch (TemplateModelException e) {
              logger.error("failed to unwrap [" + value + "] it will be ignored", e);
            }
          }
          try {
            PropertyUtils.setProperty(bean, key, value);
          } catch (Exception e) {
            logger.error("invoke set property [" + key + "] with value " + value, e);
          }
        } else {
          bean.getParameters().put(key, value);
        }
      }
    }
    try {
      if (bean.start(env.getOut())) {
        StringWriter writer = new StringWriter();
        if (body != null) {
          body.render(writer);
        }
        bean.end(env.getOut(), writer.toString());
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
