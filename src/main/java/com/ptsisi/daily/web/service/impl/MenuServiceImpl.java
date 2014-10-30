package com.ptsisi.daily.web.service.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.google.common.collect.Lists;
import com.ptsisi.common.BaseServiceImpl;
import com.ptsisi.daily.Menu;
import com.ptsisi.daily.Resource;
import com.ptsisi.daily.Role;
import com.ptsisi.daily.User;
import com.ptsisi.daily.web.service.MenuService;

public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "app")
	public List<Menu> getMenus(User user) {
		List<Resource> resources = Lists.newArrayList();
		for (Role role : user.getRoles()) {
			for (Resource resource : role.getResources()) {
				resources.add(resource);
			}
		}
		return entityDao.createCriteria(Menu.class,
				Restrictions.in("resource", resources),
				Restrictions.eq("enabled", true)).list();
	}

	@Override
	@Cacheable(value = "app")
	public Menu getMenu(Integer id) {
		return entityDao.get(Menu.class, id);
	}

	@Override
	@CachePut(value = "app")
	public void saveOrUpdate(Menu menu) {
		entityDao.saveOrUpdate(menu);
	}

	@Override
	@CacheEvict(value = "app")
	public void remove(Collection<Menu> menus) {
		entityDao.remove(menus);
	}

}
