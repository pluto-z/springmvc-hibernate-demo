package com.ptsisi.daily.web.service;

import com.ptsisi.common.BaseService;
import com.ptsisi.common.query.builder.OqlBuilder;
import com.ptsisi.daily.model.Resource;
import com.ptsisi.daily.model.Role;
import com.ptsisi.daily.model.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by zhaoding on 15-1-12.
 */
@Service
@CacheConfig(cacheNames = "resources")
public class ResourceService extends BaseService {

	@Cacheable
	public List<Resource> getResources() {
		return entityDao.getAll(Resource.class);
	}

	@Cacheable
	public List<Resource> getResources(User user) {
		OqlBuilder<Resource> builder = OqlBuilder.from(Resource.class, "resource");
		builder
				.where("exists(from " + Role.class.getName()
						+ " role where :user in elements(role.users) and resource in elements(role.resources))",
						user);
		return entityDao.search(builder);
	}

	@Cacheable(key = "#id")
	public Resource getResource(Integer id) {
		System.out.println("resource");
		return entityDao.get(Resource.class, id);
	}

	@CachePut(key = "#resource.id")
	public Resource saveOrUpdate(Resource resource) {
		entityDao.saveOrUpdate(resource);
		return resource;
	}

	@CacheEvict(allEntries = true)
	public void remove(Collection<Resource> resources) {
		entityDao.remove(resources);
	}

	@CacheEvict(allEntries = true)
	public void reload() {
	}
}
