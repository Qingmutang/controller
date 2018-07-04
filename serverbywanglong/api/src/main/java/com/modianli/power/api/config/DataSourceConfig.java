//package com.modianli.power.api.config;
//
//import org.apache.commons.dbcp.BasicDataSource;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Profile;
//
//import javax.inject.Inject;
//import javax.sql.DataSource;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Created by gao on 17-2-20.
// */
//@Configurable
//@Slf4j
//public class DataSourceConfig {
//
//  @Inject
//  private BeanConfig.MdDataSourceConfig dataSourceConfig;
//
//  @Bean
//  @Profile({"dev","test"})
//  public DataSource devDataSource() {
//	log.info("create dev dataSource");
//	BasicDataSource bds = new BasicDataSource();
//	bds.setDriverClassName(dataSourceConfig.getDriverClassName());
//	bds.setUrl(dataSourceConfig.getUrl());
//	bds.setUsername(dataSourceConfig.getUsername());
//	bds.setPassword(dataSourceConfig.getPassword());
//	return bds;
//  }
//
//}
