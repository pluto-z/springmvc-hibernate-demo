package com.ptsisi.daily.web;

import com.ptsisi.freemarker.AgentMethodModel;
import com.ptsisi.freemarker.RequestProviderMothodModel;
import freemarker.template.utility.XmlEscape;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhaoding on 14-10-23.
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:META-INF/resources.properties")
@ComponentScan(basePackages = "com.ptsisi.daily")
@Import({ DataSourceConfig.class })
@EnableTransactionManagement
public class MvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	private Environment env;

	@Override
	protected Validator getValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean =
				new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
		localValidatorFactoryBean.setValidationMessageSource(messageSource());
		return localValidatorFactoryBean;
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Bean
	public XmlEscape xmlEscape() {
		return new XmlEscape();
	}

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/views/");
		configurer.setDefaultEncoding("UTF-8");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("xml_escape", xmlEscape());
		configurer.setFreemarkerVariables(variables);
		Properties properties = new Properties();
		properties.put("template_update_delay", "0");
		properties.put("tag_syntax", "auto_detect");
		properties.put("defaultEncoding", "UTF-8");
		properties.put("url_escaping_charset", "UTF-8");
		properties.put("locale", "zh_CN");
		properties.put("boolean_format", "true,false");
		properties.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
		properties.put("date_format", "yyyy-MM-dd");
		properties.put("time_format", "HH:mm:ss");
		properties.put("number_format", "0.######");
		properties.put("whitespace_stripping", "true");
		properties.put("auto_import", "common/spring.ftl as s,common/base.ftl as b");
		configurer.setFreemarkerSettings(properties);
		return configurer;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messsages", "classpath:org/hibernate/validator/ValidationMessages");
		messageSource.setUseCodeAsDefaultMessage(false);
		messageSource.setDefaultEncoding(env.getProperty("encoding"));
		messageSource.setCacheSeconds(60);
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setCookieName("language");
		cookieLocaleResolver.setCookieMaxAge(-1);
		cookieLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
		return cookieLocaleResolver;
	}

	@Bean
	public AgentMethodModel agentMethodModel() {
		return new AgentMethodModel();
	}

	@Bean
	public RequestProviderMothodModel requestProviderMothodModel() {
		return new RequestProviderMothodModel();
	}

	@Bean
	public ViewResolver viewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		viewResolver.setCache(false);
		viewResolver.setViewClass(FreeMarkerView.class);
		viewResolver.setSuffix(".ftl");
		viewResolver.setContentType("text/html; charset=UTF-8");
		viewResolver.setExposeRequestAttributes(true);
		viewResolver.setExposeSessionAttributes(true);
		viewResolver.setRequestContextAttribute("rc");
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		attributesMap.put("fetchRequest", requestProviderMothodModel());
		attributesMap.put("agent", agentMethodModel());
		viewResolver.setAttributesMap(attributesMap);
		return viewResolver;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding(env.getProperty("encoding"));
		return resolver;
	}
}
