package com.ptsisi.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ptsisi.daily.model.Resource;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoding on 14-10-28.
 */
public class SimpleDefinitionSectionMetaSource extends AbstractDefinitionSectionMetaSource {

  //@javax.annotation.Resource
 // protected ResourceService resourceService;

  @Override
  protected Map<String, String> initOtherPermission() {
    List<Resource> resources = Lists.newArrayList();//resourceService.getResources();
    Map<String, String> permissions = Maps.newHashMap();
    for (Resource resource : resources) {
      if (StringUtils.isNotBlank(resource.getPermission())) {
        permissions.put(resource.getValue(), resource.getPermission());
      }
    }
    return permissions;
  }
}
