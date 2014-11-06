package com.ptsisi.ui.template;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import freemarker.core.CollectionAndSequence;
import freemarker.ext.beans.CollectionModel;
import freemarker.ext.beans.MapModel;
import freemarker.ext.util.ModelFactory;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class CustomObjectWrapper extends DefaultObjectWrapper {

  private boolean altMapWrapper;

  public CustomObjectWrapper(boolean altMapWrapper) {
    this.altMapWrapper = altMapWrapper;
  }

  public TemplateModel wrap(Object obj) throws TemplateModelException {
    if (obj == null) { return super.wrap(null); }
    if (obj instanceof List<?>) { return new CollectionModel((Collection<?>) obj, this); }
    // 使得set等集合可以排序
    if (obj instanceof Collection<?>) { return new SimpleSequence((Collection<?>) obj, this); }
    if (obj instanceof Map<?, ?>) {
      if (altMapWrapper) {
        return new FriendlyMapModel((Map<?, ?>) obj, this);
      } else {
        return new MapModel((Map<?, ?>) obj, this);
      }
    }
    return super.wrap(obj);
  }

  @SuppressWarnings("rawtypes")
  protected ModelFactory getModelFactory(Class clazz) {
    if (altMapWrapper && Map.class.isAssignableFrom(clazz)) { return FriendlyMapModel.FACTORY; }
    return super.getModelFactory(clazz);
  }

  private final static class FriendlyMapModel extends MapModel implements TemplateHashModelEx, TemplateMethodModelEx,
      AdapterTemplateModel {
    static final ModelFactory FACTORY = new ModelFactory() {
      public TemplateModel create(Object object, ObjectWrapper wrapper) {
        return new FriendlyMapModel((Map<?, ?>) object, (CustomObjectWrapper) wrapper);
      }
    };

    public FriendlyMapModel(Map<?, ?> map, CustomObjectWrapper wrapper) {
      super(map, wrapper);
    }

    // Struts2将父类的&& super.isEmpty()省去了，原因不知
    public boolean isEmpty() {
      return ((Map<?, ?>) object).isEmpty();
    }

    // 此处实现与MapModel不同，MapModel中复制了一个集合
    // 影响了?keySet,?size方法
    protected Set<?> keySet() {
      return ((Map<?, ?>) object).keySet();
    }

    // add feature
    public TemplateCollectionModel values() {
      return new CollectionAndSequence(new SimpleSequence(((Map<?, ?>) object).values(), wrapper));
    }
  }
}
