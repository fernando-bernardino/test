package com.voyanta.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import com.voyanta.test.data.HibernateConfiguration;

public class HibernateConfigurationTest {
	@Mock
	Environment environment;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		setEnvironmentProperties();
	}

	@Test
	@Ignore
	public void dataSource_allPropertiesFromEnvironment_propertiesAreSet(){
		//	given
		HibernateConfiguration configuration = new HibernateConfiguration();
		configuration.setEnvironment(environment);
		
		//	when
		DataSource dataSource = configuration.dataSource();
		
		assertAllPropertiesAreSet((DriverManagerDataSource) dataSource);
	}

	private void setEnvironmentProperties() {
		when(environment.getRequiredProperty("hibernate.dialect")).thenReturn("dialect");
		when(environment.getRequiredProperty("hibernate.show_sql")).thenReturn("show");
		when(environment.getRequiredProperty("hibernate.format_sql")).thenReturn("format");
		
		when(environment.getRequiredProperty("jdbc.driverClassName")).thenReturn("com.mysql.jdbc.Driver");
		when(environment.getRequiredProperty("jdbc.url")).thenReturn("url");
		when(environment.getRequiredProperty("jdbc.username")).thenReturn("username");
		when(environment.getRequiredProperty("jdbc.password")).thenReturn("password");
	}
	
	private void assertAllPropertiesAreSet(DriverManagerDataSource dataSource) {
		Properties properties = dataSource.getConnectionProperties();
		
		assertEquals(properties.getProperty("hibernate.dialect"), "dialect");
		assertEquals(properties.getProperty("hibernate.show_sql"), "show");
		assertEquals(properties.getProperty("hibernate.format_sql"), "format");
		
		assertEquals(properties.getProperty("jdbc.driverClassName"), "com.mysql.jdbc.Driver");
		assertEquals(properties.getProperty("jdbc.url"), "url");
		assertEquals(properties.getProperty("jdbc.username"), "username");
		assertEquals(properties.getProperty("jdbc.password"), "password");
	}
	
	@Test
	@Ignore
	public void transactionManager_sessionFactoryPassed_isSet(){
		//	given
		HibernateConfiguration configuration = new HibernateConfiguration();
		SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
		
		//	when
		HibernateTransactionManager tx = configuration.transactionManager(sessionFactory);
		
		assertEquals(sessionFactory, tx.getSessionFactory());
	}
}
