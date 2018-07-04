package com.modianli.power.config;

import com.modianli.power.domain.jpa.UserAccount;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Configuration
//@EnableAspectJAutoProxy
//TODO fix maven compiler change to AdviceMode.ASPECTJ
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@EnableJpaRepositories(basePackages = {"com.modianli.power.persistence.repository.jpa"})
@EnableJpaAuditing(auditorAwareRef = "auditor")
@EntityScan(basePackageClasses = {UserAccount.class, Jsr310JpaConverters.class})
@Slf4j
public class JpaConfig
//	implements TransactionManagementConfigurer
{

//  @Inject
//  private DataSource dataSource;
//
//  @Inject
//  private JpaProperties properties;
//
//  @Bean
//  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//	LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//	emf.setDataSource(dataSource);
//	emf.setMappingResources("META-INF/orm.xml");
////	emf.setPersistenceXmlLocation("classpath:META-INF/persistence.xml.bak");
//	emf.setPackagesToScan("com.modianli.power.domain.jpa", "org.springframework.data.jpa.convert.threeten");
//	emf.setPersistenceProvider(new HibernatePersistenceProvider());
//	emf.setJpaProperties(jpaProperties());
//	emf.setJpaDialect(new HibernateJpaDialect());
//	emf.setJpaVendorAdapter(jpaVendorAdapter());
//	return emf;
//  }
//
//  public JpaVendorAdapter jpaVendorAdapter() {
//	AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//	adapter.setDatabasePlatform(this.properties.getDatabasePlatform());
//	return adapter;
//  }
//
//  private Properties jpaProperties() {
//	Properties extraProperties = new Properties();
//	extraProperties.put("hibernate.format_sql", properties.getProperties().get("hibernate.format_sql"));
//	extraProperties.put("hibernate.show_sql", properties.getProperties().get("hibernate.show_sql"));
//	extraProperties.put("hibernate.hbm2ddl.auto", properties.getProperties().get("hibernate.hbm2ddl.auto"));
//	extraProperties.put("hibernate.dialect", properties.getProperties().get("hibernate.dialect"));
//	return extraProperties;
//  }

//  @Inject
//  private EntityManagerFactory entityManagerFactory;

//  @Bean
//  public PlatformTransactionManager transactionManager() {
//	return new JpaTransactionManager(entityManagerFactory().getObject());
//  }

//  @Override
//  public PlatformTransactionManager annotationDrivenTransactionManager() {
//	return transactionManager();
//  }

  @Bean("auditor")
  public AuditorAware<UserAccount> auditor() {
	if (log.isDebugEnabled()) {
	  log.debug("get current auditor@@@@");
	}
	return () -> {

	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (auth == null) {
		return null;
	  }

	  if (auth instanceof AnonymousAuthenticationToken) {
		return null;
	  }
	  return (UserAccount) auth.getPrincipal();
	};

  }

}
