package com.ptsisi.daily.web;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * Created by zhaoding on 14-10-23.
 */
@Configuration
@PropertySource("classpath:META-INF/db.properties")
public class DataSourceConfig {

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(env.getProperty("db.driverClassName"));
		dataSource.setJdbcUrl(env.getProperty("db.url"));
		dataSource.setUser(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		dataSource.setMaxPoolSize(40);
		dataSource.setMinPoolSize(1);
		dataSource.setInitialPoolSize(1);
		dataSource.setMaxIdleTime(20);
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() throws Exception {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.ptsisi.daily.model" });
		Properties props = new Properties();
		props.load(new ClassPathResource("META-INF/hibernate.properties").getInputStream());
		sessionFactory.setHibernateProperties(props);
		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager txManager() throws Exception {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory().getObject());
		return txManager;
	}
}
