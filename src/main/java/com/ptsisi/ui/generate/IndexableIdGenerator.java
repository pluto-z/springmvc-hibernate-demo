package com.ptsisi.ui.generate;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

public class IndexableIdGenerator implements UIIdGenerator {

  private String seed;

  private Map<Class<?>, UIIndex> uiIndexes = Maps.newHashMap();

  public IndexableIdGenerator(String seed) {
    this.seed = seed;
  }

  public String generate(Class<?> clazz) {
    UIIndex index = uiIndexes.get(clazz);
    if (null == index) {
      index = new UIIndex(StringUtils.uncapitalize(clazz.getSimpleName()));
      uiIndexes.put(clazz, index);
    }
    return index.genId(seed);
  }

  private static class UIIndex {
    int index = 0;
    final String name;

    UIIndex(String name) {
      super();
      this.name = name;
    }

    public String genId(String seed) {
      index++;
      return StringUtils.join(name, seed, String.valueOf(index));
    }
  }

}
