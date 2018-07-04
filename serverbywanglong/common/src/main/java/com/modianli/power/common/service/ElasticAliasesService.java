package com.modianli.power.common.service;

import com.google.common.collect.Lists;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.AliasQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/3/16.
 */
@Service
@Slf4j
public class ElasticAliasesService {

  @Inject
  private ElasticsearchTemplate elasticsearchTemplate;

  public void saveAlias(String indexName,String aliasName){
	Assert.notNull(indexName,"indexName cannot be null");
	Assert.notNull(aliasName,"aliasName cannot be null");

	if (elasticsearchTemplate.indexExists(indexName)){
	  addAlias(indexName, aliasName);
	}
  }

  public void updateAlias(String indexName,String aliasName,String targetAliasName){
	Assert.notNull(indexName,"indexName cannot be null");
	Assert.notNull(aliasName,"aliasName cannot be null");
	Assert.notNull(targetAliasName,"targetAliasName cannot be null");

	AliasQuery aliasQuery = new AliasQuery();
	aliasQuery.setIndexName(indexName);
	aliasQuery.setAliasName(aliasName);
	if (elasticsearchTemplate.removeAlias(aliasQuery)){
	  addAlias(indexName, targetAliasName);
	}
  }

  private void addAlias(String indexName,String aliasName){
	AliasQuery query = new AliasQuery();
	query.setIndexName(indexName);
	query.setAliasName(aliasName);

	elasticsearchTemplate.addAlias(query);
	elasticsearchTemplate.refresh(indexName);
  }

  public List<String> getIndexes(){
	List list = Lists.newArrayList();
	list.add("quote_v1");
	list.add("category_property_v1");
	list.add("equipment_v1");
	list.add("equipment_v1");
	list.add("product_v1");
	return list;
  }
}


