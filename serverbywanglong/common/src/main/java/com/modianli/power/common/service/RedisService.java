package com.modianli.power.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by dell on 2017/5/2.
 */
@Component
public class RedisService {

  @Value("${modian.url}")
  private String url;

  public static final String prefix = "prerender:";

  @Inject
  private StringRedisTemplate stringRedisTemplate;

  public String getKeyPattern(String type, String uuid) {
	String key = prefix + url;
	switch (type) {
	  case "All":
		key = key + "*";
		break;
	  case "HOME_PAGE":
		break;
	  case "SHOPPING_PAGE":
		key = key + "quotesMall/index*";
		break;
	  case "PRODUCT_LISTS":
		key = key + "quotesMall/lists*";
		break;
	  case "REQUIREMENT_LISTS":
		key = key + "requirement/lists*";
		break;
	  case "ENTERPRISE_LISTS":
		key = key + "enterprise/lists*";
		break;
	  case "CASE":
		if (uuid == null) {
		  return null;
		}
		key += "enterprise/detail/" + uuid + "/case*";
		break;
	  case "PRODUCT_DETAILS":
		if (uuid == null) {
		  return null;
		}
		key = key + "quotesMall/detail/" + uuid + "*";
		break;
	  case "REQUIREMENT_DETAILS":
		if (uuid == null) {
		  return null;
		}
		key = key + "requirement/detail/" + uuid + "*";
		break;
	  case "ENTERPRISE_DETAILS":
		if (uuid == null) {
		  return null;
		}
		key = key + "enterprise/detail/" + uuid + "*";
		break;
	  default:
		return null;
	}
	return key;
  }

  public void deleteKeyByPattern(String pattern) {
	Set keys = stringRedisTemplate.keys(pattern);
	stringRedisTemplate.delete(keys);
  }

  public void deleteList() {
	Set keys1 = stringRedisTemplate.keys(getKeyPattern("PRODUCT_LISTS", null));
	stringRedisTemplate.delete(keys1);
	Set keys2 = stringRedisTemplate.keys(getKeyPattern("REQUIREMENT_LISTS", null));
	stringRedisTemplate.delete(keys2);
	Set keys3 = stringRedisTemplate.keys(getKeyPattern("ENTERPRISE_LISTS", null));
	stringRedisTemplate.delete(keys3);
  }

  /**
   * 删除需求的预渲染
   */
  public void deleteRequirement(String uuid) {
	String pattern = getKeyPattern("REQUIREMENT_DETAILS", uuid);
	deleteKeyByPattern(pattern);
  }

  /**
   * 删除企业的预渲染
   */
  public void deleteEnterprise(String uuid) {
	String pattern = getKeyPattern("ENTERPRISE_DETAILS", uuid);
	deleteKeyByPattern(pattern);
  }

  /**
   * 删除产品预渲染
   */
  public void deleteProduct(String uuid) {
	String pattern = getKeyPattern("PRODUCT_DETAILS", uuid);
	deleteKeyByPattern(pattern);
  }

  /**
   * 删除报价商城的预渲染
   */
  public void deleteShoppingPage() {
	String pattern = getKeyPattern("SHOPPING_PAGE", null);
	deleteKeyByPattern(pattern);
  }

  /**
   * 删除首页的预渲染
   */
  public void deleteHomePage() {
	String pattern = getKeyPattern("HOME_PAGE", null);
	deleteKeyByPattern(pattern);
  }

  /**
   * 删除企业列表的预渲染
   */
  public void deleteEnterpriseListPage() {
	String pattern = getKeyPattern("ENTERPRISE_LISTS", null);
	deleteKeyByPattern(pattern);
  }

  /**
   * 删除所有的预渲染
   */
  public void deleteAll() {
	String pattern = getKeyPattern("All", null);
	deleteKeyByPattern(pattern);
  }

  /**
   * 删除企业案例
   */
  public void deleteEnterpriseCase(String uuid) {
	String pattern = getKeyPattern("CASE", uuid);
	deleteKeyByPattern(pattern);
  }
}
