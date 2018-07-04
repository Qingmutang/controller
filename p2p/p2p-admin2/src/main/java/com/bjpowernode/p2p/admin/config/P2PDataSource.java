package com.bjpowernode.p2p.admin.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 配置p2p数据源
 * 
 * @author 动力节点705班
 *
 */
@Configuration
@MapperScan(basePackages="com.bjpowernode.p2p.loan.mapper", sqlSessionTemplateRef="p2pSessionTemplate")
public class P2PDataSource {

	@Autowired
	private DataSourceConfig dataSourceConfig;

	/**
	 * 配置数据源（p2padmin）
	 * 
	 * @return
	 */
	@Bean
	public DruidDataSource p2pDataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(dataSourceConfig.getP2pUrl());
		druidDataSource.setUsername(dataSourceConfig.getP2pUserName());
		druidDataSource.setPassword(dataSourceConfig.getP2pPassword());
		druidDataSource.setDriverClassName(dataSourceConfig.getP2pDriver());
		return druidDataSource;
	}
	
	/**
	 * 配置 SqlSessionFactory
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory p2pSessionFactory () throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(p2pDataSource());
		return sqlSessionFactoryBean.getObject();
	}
	
	/**
	 * 配置 SqlSessionTemplate
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionTemplate p2pSessionTemplate () throws Exception {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(p2pSessionFactory());
		return sqlSessionTemplate;
	}
}
