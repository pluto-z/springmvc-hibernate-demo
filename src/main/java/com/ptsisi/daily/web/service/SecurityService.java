package com.ptsisi.daily.web.service;

import com.ptsisi.daily.Menu;
import com.ptsisi.daily.Resource;
import com.ptsisi.daily.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface SecurityService {

	public List<Menu> getMenus(Collection<Resource> resources);

	public Menu getMenu(Integer id);

	public void saveOrUpdateMenu(Menu menu);

	public void removeMenus(Collection<Menu> menus);

	public Set<Resource> getResources(User user);

	public List<Resource> getResources();

	public void saveOrUpdateResource(Resource resource);

	public void removeResources(Collection<Resource> resources);
}
