package com.ptsisi.hibernate;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ptsisi.common.metadata.Model;
import com.ptsisi.common.metadata.impl.DefaultModelMeta;

@Component
public class HibernateModelMeta extends DefaultModelMeta implements ApplicationContextAware {

  @Autowired
  ApplicationContext context;

  @PostConstruct
  public void init() throws Exception {
    HibernateEntityContext entityContext = new HibernateEntityContext();
    Map<String, SessionFactory> factories = context.getBeansOfType(SessionFactory.class);
    for (Map.Entry<String, SessionFactory> entry : factories.entrySet()) {
      entityContext.initFrom(entry.getValue());
    }
    setContext(entityContext);
    Model.setMeta(this);
    context = null;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

}
