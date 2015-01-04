package com.ptsisi.daily;

import com.ptsisi.daily.model.UserBean;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class EhcacheTest {

	@Test
	public void test() throws IOException {
		net.sf.ehcache.CacheManager ehcacheManager
				= new net.sf.ehcache.CacheManager(new ClassPathResource("ehcache.xml").getInputStream());

		//创建Spring的CacheManager
		EhCacheCacheManager cacheCacheManager = new EhCacheCacheManager();
		//设置底层的CacheManager
		cacheCacheManager.setCacheManager(ehcacheManager);

		Integer id = 1;
		User user = new UserBean();
		user.setId(id);
		user.setFullName("aaa");
		user.setEmail("aaa@gmail.com");
		//根据缓存名字获取Cache
		Cache cache = cacheCacheManager.getCache("app");
		//往缓存写数据
		cache.put(id, user);
		//从缓存读数据
		Assert.assertNotNull(cache.get(id, User.class));
	}
}
