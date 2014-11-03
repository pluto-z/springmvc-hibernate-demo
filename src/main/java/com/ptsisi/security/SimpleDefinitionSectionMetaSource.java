package com.ptsisi.security;

import com.google.common.collect.Maps;
import com.ptsisi.daily.Resource;
import com.ptsisi.daily.web.service.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoding on 14-10-28.
 */
public class SimpleDefinitionSectionMetaSource extends AbstractDefinitionSectionMetaSource {

  @Autowired
  protected SecurityService securityService;

  @Override
  protected Map<String, String> initOtherPermission() {
    List<Resource> resources = securityService.getResources();
    Map<String, String> permissions = Maps.newHashMap();
    for (Resource resource : resources) {
      if (StringUtils.isNotBlank(resource.getPermission())) {
        permissions.put(resource.getValue(), resource.getPermission());
      }
    }
    return permissions;
  }
}
