package com.ptsisi.daily.web;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by zhaoding on 14-10-24.
 */
@Configuration
@ComponentScan(basePackages = { "com.ptsisi.daily.dao", "com.ptsisi.daily.service" })
@EnableCaching(proxyTargetClass = true)
public class CacheConfig implements CachingConfigurer {

	@Bean
	public EhCacheManagerFactoryBean ehcacheManagerFactory() {
		EhCacheManagerFactoryBean ehCacheManagerFactory = new EhCacheManagerFactoryBean();
		ehCacheManagerFactory.setShared(true);
		ehCacheManagerFactory.setConfigLocation(new ClassPathResource("ehcache.xml"));
		return ehCacheManagerFactory;
	}

	@Bean
	@Override public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehcacheManagerFactory().getObject());
	}

	@Bean
	@Override public CacheResolver cacheResolver() {
		return new SimpleCacheResolver(cacheManager());
	}

	@Bean
	@Override public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	@Bean
	@Override public CacheErrorHandler errorHandler() {
		return new SimpleCacheErrorHandler();
	}
}
