package com.modianli.power.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-3-3.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = {"com.modianli.power.persistence.repository.es"})
@Slf4j
public class ElasticsearchConfig {

}