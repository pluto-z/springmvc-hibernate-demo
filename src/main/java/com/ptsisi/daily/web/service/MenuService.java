package com.ptsisi.daily.web.service;

import java.util.Collection;
import java.util.List;

import com.ptsisi.daily.Menu;
import com.ptsisi.daily.User;

public interface MenuService {

	public List<Menu> getMenus(User user);
	
	public Menu getMenu(Integer id);
	
	public void saveOrUpdate(Menu menu);

	public void remove(Collection<Menu> menus);
}
