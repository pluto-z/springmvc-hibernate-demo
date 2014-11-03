package com.ptsisi.daily.web.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ptsisi.common.BaseServiceImpl;
import com.ptsisi.daily.Menu;
import com.ptsisi.daily.Resource;
import com.ptsisi.daily.Role;
import com.ptsisi.daily.User;
import com.ptsisi.daily.web.service.SecurityService;
import com.ptsisi.daily.web.utils.HierarchyEntityUtils;
import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class SecurityServiceImpl extends BaseServiceImpl implements SecurityService {

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "app")
	public List<Menu> getMenus(Collection<Resource> resources) {
		if (resources.isEmpty()) return Lists.newArrayList();
		List<String> entries = (List<String>) CollectionUtils
				.collect(resources, new BeanToPropertyValueTransformer("value"));
		List<Menu> menus = resources.isEmpty() ? Lists.newArrayList() : entityDao.createCriteria(Menu.class,
				Restrictions.in("entry", entries),
				Restrictions.eq("enabled", true)).list();
		addParentMenus(menus);
		return menus;
	}

	private List<Menu> addParentMenus(Collection<Menu> menus) {
		HierarchyEntityUtils.addParent(menus);
		List<Menu> menuList = Lists.newArrayList(menus);
		/*for (Menu menu : menuList) {
			menu.getChildren().size();
		}*/
		Collections.sort(menuList);
		return menuList;
	}

	@Override
	@Cacheable(value = "app")
	public Menu getMenu(Integer id) {
		return entityDao.get(Menu.class, id);
	}

	@Override
	@CachePut(value = "app")
	public void saveOrUpdateMenu(Menu menu) {
		entityDao.saveOrUpdate(menu);
	}

	@Override
	@CacheEvict(value = "app")
	public void removeMenus(Collection<Menu> menus) {
		entityDao.remove(menus);
	}

	@Override
	@Cacheable(value = "app")
	public Set<Resource> getResources(User user) {
		Set<Resource> resources = Sets.newHashSet();
		for (Role role : user.getRoles()) {
			resources.addAll(role.getResources());
		}
		return resources;
	}

	@Override public List<Resource> getResources() {
		return entityDao.getAll(Resource.class);
	}

	@Override
	@CachePut(value = "app")
	public void saveOrUpdateResource(Resource resource) {
		entityDao.saveOrUpdate(resource);
	}

	@Override
	@CacheEvict(value = "app")
	public void removeResources(Collection<Resource> resources) {
		entityDao.remove(resources);
	}
}
