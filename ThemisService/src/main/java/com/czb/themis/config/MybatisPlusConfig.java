package com.czb.themis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisPlus 自定义配置
 *
 * @author ljmatlight
 * @date 2018/6/19
 */
@Configuration
@MapperScan("com.czb.themis.base.dao")
public class MybatisPlusConfig {


}
