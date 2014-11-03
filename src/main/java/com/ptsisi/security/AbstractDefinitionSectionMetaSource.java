package com.ptsisi.security;

import java.util.Map;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;

/**
 * 借助spring {@link org.springframework.beans.factory.FactoryBean} 对apache
 * shiro的premission进行动态创建
 * 
 * @author maurice
 */
public abstract class AbstractDefinitionSectionMetaSource implements FactoryBean<Section> {

  private String filterChainDefinitions;

  public void setFilterChainDefinitions(String filterChainDefinitions) {
    this.filterChainDefinitions = filterChainDefinitions;
  }

  @Override
  public Section getObject() throws BeansException {
    Ini ini = new Ini();
    ini.load(filterChainDefinitions);

    Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
    if (CollectionUtils.isEmpty(section)) {
      section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
    }
    Map<String, String> resources = initOtherPermission();
    // 循环数据库资源的url
    if (resources != null && !resources.isEmpty()) {
      section.putAll(resources);
    }
    return section;
  }

  protected abstract Map<String, String> initOtherPermission();

  @Override
  public Class<Section> getObjectType() {
    return Section.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

}
