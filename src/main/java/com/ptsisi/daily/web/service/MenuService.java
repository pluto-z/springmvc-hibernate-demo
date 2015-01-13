package com.ptsisi.daily.web.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ptsisi.common.BaseService;
import com.ptsisi.daily.model.Menu;
import com.ptsisi.daily.model.Resource;
import com.ptsisi.daily.web.utils.HierarchyEntityUtils;
import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@CacheConfig(cacheNames = "menus")
public class MenuService extends BaseService {

	@SuppressWarnings("unchecked")
	@Cacheable
	public List<Menu> getMenus(Collection<Resource> resources) {
		if (resources.isEmpty()) return Lists.newArrayList();
		List<Menu> menus = entityDao.getAll(Menu.class);
		Set<Menu> user_menuSet = Sets.newHashSet();
		Map<Integer, Menu> user_menus = Maps.newHashMap();
		List<String> entries = (List<String>) CollectionUtils.collect(resources,
				new BeanToPropertyValueTransformer("value"));
		for (Menu menu : menus) {
			if (entries.contains(menu.getEntry())) user_menuSet.add(menu);
		}
		HierarchyEntityUtils.addParent(user_menuSet);
		for (Menu menu : user_menuSet) {
			user_menus.put(menu.getId(), menu.clone());
		}
		List<Menu> menuList = menuRecursion(user_menus);
		Collections.sort(menuList);
		for (Menu menu : menuList) {
			menu.sort();
		}
		return menuList;
	}

	private List<Menu> menuRecursion(Map<Integer, Menu> menus) {
		List<Menu> menuList = Lists.newArrayList();
		for (Menu menu : menus.values()) {
			if (menu.getParent() == null) {
				menuList.add(menu);
			} else {
				menus.get(menu.getParent().getId()).getChildren().add(menu);
			}
		}
		return menuList;
	}

	@Cacheable(key = "#id")
	public Menu getMenu(Integer id) {
		System.out.println("menu");
		return entityDao.get(Menu.class, id);
	}

	@CachePut(key = "#menu.id")
	public Menu saveOrUpdate(Menu menu) {
		entityDao.saveOrUpdate(menu);
		return menu;
	}

	@CacheEvict(allEntries = true, beforeInvocation = true)
	public void remove(Collection<Menu> menus) {
		entityDao.remove(menus);
	}

	@CacheEvict(allEntries = true)
	public void reload() {
	}
}
