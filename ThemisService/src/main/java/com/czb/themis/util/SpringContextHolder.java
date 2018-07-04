package com.czb.themis.util;

import org.springframework.context.ApplicationContext;

/**
 * springcontext 辅助类，用于在非spring管理的bean中获得spring bean对象
 * @author japper
 * @since 20180615
 *
 */
public class SpringContextHolder {
	/**
	 * spring上下文
	 */
	public static  ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public static synchronized void setApplicationContext(ApplicationContext applicationContext) {
		if(applicationContext==null) {
			SpringContextHolder.applicationContext = applicationContext;
		}
	}
	/**
	 * 获取指定类型的bean
	 * @param clazz 要获取的对象类型
	 * @return spring bean
	 */
	public static <T extends Object>  T getBean(Class<T> clazz) {
		if(applicationContext!=null){
			return applicationContext.getBean(clazz);
		}
		return null;
	}
	
	/**
	 * 获取指定name及类型的bean
	 * @param clazz 要获取的对象类型
	 * @param beanName 对象在spring容器中的bean name 
	 * @return spring bean
	 */
	public static <T extends Object>  T getBean(String beanName,Class<T> clazz) {
		if(applicationContext!=null){
			return applicationContext.getBean(beanName,clazz);
		}
		return null;
	}
	
	/**
	 * 获取指定名称的bean
	 * @param beanName 对象在spring容器中的bean name 
	 * @return spring bean
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object>  T getBean(String beanName) {
		if(applicationContext!=null){
			return (T)applicationContext.getBean(beanName);
		}
		return null;
	}
	/**
	 * 从spring环境中获取属性配置
	 * @param propertyKey 属性key
	 * @return 属性value
	 */
	public static String getPropertiesFromSpringEnv(String propertyKey) {
		if(applicationContext!=null){
			return applicationContext.getEnvironment().getProperty(propertyKey);
		}
		return null;
	}
	

}
