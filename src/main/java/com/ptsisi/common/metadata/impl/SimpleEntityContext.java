package com.ptsisi.common.metadata.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ptsisi.common.lang.ClassLoaders;
import com.ptsisi.common.metadata.EntityType;
import com.ptsisi.common.metadata.Type;

public class SimpleEntityContext extends AbstractEntityContext {

  private static final Logger logger = LoggerFactory.getLogger(SimpleEntityContext.class);

  @PostConstruct
  public void init() throws Exception {
    Properties props = new Properties();
    try {
      InputStream is = ClassLoaders.getResourceAsStream("model.properties", getClass());
      if (null != is) {
        props.load(is);
      }
    } catch (IOException e) {
      logger.error("read error model.properties");
    }

    if (!props.isEmpty()) {
      logger.info("Using model.properties initialize Entity Context.");
      for (Map.Entry<Object, Object> entry : props.entrySet()) {
        String key = (String) entry.getKey();
        String value = (String) entry.getValue();
        EntityType entityType = null;
        try {
          entityType = new EntityType(key, Class.forName(value), "id");
        } catch (ClassNotFoundException e) {
          logger.error(value + " was not correct class name", e);
        }
        entityType.setPropertyTypes(new HashMap<String, Type>());
        entityTypes.put(key, entityType);
      }
    }

  }
}
