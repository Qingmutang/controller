package com.bjpowernode.p2p.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据源配置的映射
 * 
 * @author 动力节点705班
 *
 */
@Component
@ConfigurationProperties(prefix="spring.datasource")
public class DataSourceConfig {

	/**p2padmin数据库配置*/
	private String adminUrl;
	private String adminUserName;
	private String adminPassword;
	private String adminDriver;

	/**p2p数据库配置*/
	private String p2pUrl;
	private String p2pUserName;
	private String p2pPassword;
	private String p2pDriver;

	public String getAdminUrl() {
		return adminUrl;
	}

	public void setAdminUrl(String adminUrl) {
		this.adminUrl = adminUrl;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getAdminDriver() {
		return adminDriver;
	}

	public void setAdminDriver(String adminDriver) {
		this.adminDriver = adminDriver;
	}

	public String getP2pUrl() {
		return p2pUrl;
	}

	public void setP2pUrl(String p2pUrl) {
		this.p2pUrl = p2pUrl;
	}

	public String getP2pUserName() {
		return p2pUserName;
	}

	public void setP2pUserName(String p2pUserName) {
		this.p2pUserName = p2pUserName;
	}

	public String getP2pPassword() {
		return p2pPassword;
	}

	public void setP2pPassword(String p2pPassword) {
		this.p2pPassword = p2pPassword;
	}

	public String getP2pDriver() {
		return p2pDriver;
	}

	public void setP2pDriver(String p2pDriver) {
		this.p2pDriver = p2pDriver;
	}
}
