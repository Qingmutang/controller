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
 * 配置p2padmin数据源
 * 
 * @author 动力节点705班
 *
 */
@Configuration
@MapperScan(basePackages="com.bjpowernode.p2p.admin.mapper", sqlSessionTemplateRef="p2padminSessionTemplate")
public class P2PAdminDataSource {
	
	@Autowired
	private DataSourceConfig dataSourceConfig;

	/**
	 * 配置数据源（p2padmin）
	 * 
	 * @return
	 */
	@Bean
	public DruidDataSource p2padminDataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(dataSourceConfig.getAdminUrl());
		druidDataSource.setUsername(dataSourceConfig.getAdminUserName());
		druidDataSource.setPassword(dataSourceConfig.getAdminPassword());
		druidDataSource.setDriverClassName(dataSourceConfig.getAdminDriver());
		return druidDataSource;
	}
	
	/**
	 * 配置 SqlSessionFactory
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory p2padminSessionFactory () throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(p2padminDataSource());
		return sqlSessionFactoryBean.getObject();
	}
	
	/**
	 * 配置 SqlSessionTemplate
	 * 
	 * <bean id="p2padminSessionTemplate" class="org.ibatis.xxx.SqlSessionTemplate">
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionTemplate p2padminSessionTemplate () throws Exception {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(p2padminSessionFactory());
		return sqlSessionTemplate;
	}
}
