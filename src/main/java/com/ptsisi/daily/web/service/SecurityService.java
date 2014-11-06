package com.ptsisi.daily.web.service;

import java.util.Collection;
import java.util.List;

import com.ptsisi.daily.Menu;
import com.ptsisi.daily.Resource;

public interface SecurityService {

  public List<Menu> getMenus(Collection<Resource> resources);

  public Menu getMenu(Integer id);

  public void saveOrUpdateMenu(Menu menu);

  public void removeMenus(Collection<Menu> menus);

  public List<Resource> getResources();

  public void saveOrUpdateResource(Resource resource);

  public void removeResources(Collection<Resource> resources);

  public List<Menu> getMenus();
}
