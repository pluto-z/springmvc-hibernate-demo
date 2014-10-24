package com.ptsisi.daily.web;

import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;
import org.springframework.web.util.Log4jWebConfigurer;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by zhaoding on 14-10-23.
 */
public class WebappInitializer implements WebApplicationInitializer {

	@Override public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.setInitParameter(Log4jWebConfigurer.CONFIG_LOCATION_PARAM, "classpath:META-INF/log4j.properties");
		servletContext.setInitParameter(Log4jWebConfigurer.REFRESH_INTERVAL_PARAM, "60000");
		servletContext.addListener(new Log4jConfigListener());
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(MvcConfig.class, CacheConfig.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"dispatcher", new DispatcherServlet(rootContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("*.action");

		FilterRegistration characterEncodingFilter =
				servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
		characterEncodingFilter.setInitParameter("encoding", "utf-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

		FilterRegistration simplePageCachingFilter =
				servletContext.addFilter("SimplePageCachingFilter", SimplePageCachingFilter.class);
		simplePageCachingFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "*.html", "*.do");

		FilterRegistration openSessionInViewFilter = servletContext
				.addFilter("OpenSessionInViewFilter", OpenSessionInViewFilter.class);
		openSessionInViewFilter.setInitParameter("singleSession", "true");
		openSessionInViewFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "*.action");

	}
}
