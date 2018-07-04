/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.modianli.power.DTOUtils;
import com.modianli.power.RedisKeyConstants;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.Permission;
import com.modianli.power.domain.jpa.Role;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.CertificateDataDetails;
import com.modianli.power.model.IndustryCategoryDetails;
import com.modianli.power.model.IndustryDataDetails;
import com.modianli.power.persistence.repository.jpa.IndustryCategoryRepository;
import com.modianli.power.persistence.repository.jpa.PermissionRepository;
import com.modianli.power.persistence.repository.jpa.RoleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@RestController
@RequestMapping(value = ApiConstants.URI_API + "/public/refs")
public class DataReferenceController {

  private static final Logger log = LoggerFactory.getLogger(DataReferenceController.class);

  @Inject
  private RoleRepository roleRepository;

  @Inject
  private PermissionRepository permissionRepository;
  @Inject
  private IndustryCategoryRepository industryCategoryRepository;

  @GetMapping(value = "/enterpriseTypes")
  public Enterprise.Type[] enterpriseTypes() {
	return Enterprise.Type.values();
  }

  @RequestMapping(value = {ApiConstants.URI_ROLES}, method = RequestMethod.GET)
  @ResponseBody
  public List<String> allActiveRoles() {

	if (log.isDebugEnabled()) {
	  log.debug(" find ref roles ");
	}

	List<Role> roles = roleRepository.findByActiveIsTrue();

	List<String> roleNames = new ArrayList<>();
	for (Role r : roles) {
	  roleNames.add(r.getName());
	}

	if (log.isDebugEnabled()) {
	  log.debug("public reference roles@" + roleNames);
	}

	return roleNames;
  }

  @RequestMapping(value = {ApiConstants.URI_PERMISSIONS}, method = RequestMethod.GET)
  @ResponseBody
  public List<String> allActivePermissions() {

	if (log.isDebugEnabled()) {
	  log.debug(" find ref permissions... ");
	}

	List<Permission> perms = permissionRepository.findByActiveIsTrue();

	List<String> permNames = new ArrayList<>();
	for (Permission r : perms) {
	  permNames.add(r.getName());
	}

	if (log.isDebugEnabled()) {
	  log.debug("public ref permissions@" + permNames);
	}

	return permNames;
  }
	@RequestMapping(value = {ApiConstants.URL_IN_CATEGORY}, method = RequestMethod.GET)
	@ResponseBody
	@Cacheable(value= RedisKeyConstants.CACHE_KEY + "findCategory",key = "#root.methodName")
	public IndustryDataDetails findCategory(){
	  List<IndustryCategoryDetails> industryCategoryList= DTOUtils.mapList(industryCategoryRepository.findAll(),IndustryCategoryDetails.class);
	  IndustryDataDetails industryData=new IndustryDataDetails("企业类型",industryCategoryList);
	  return industryData;
	}
	@RequestMapping(value = {ApiConstants.URL_CERTIFICATE}, method = RequestMethod.GET)
	@ResponseBody
	@Cacheable(value=RedisKeyConstants.CACHE_KEY+"findCertificate",key = "#root.method.name")
	public CertificateDataDetails findCertificate(){
		List<Map<String, String>> list = Lists.newArrayList();
		Map<String, String> map = Maps.newHashMap();
		Map<String, String> map2 = Maps.newHashMap();
		map2.put("id", "");
		map2.put("name", "全部");
		list.add(map2);
		map.put("id", "APPROVED");
		map.put("name", "认证");
		list.add(map);
		Map<String, String> map1 = Maps.newHashMap();
		map1.put("id", "UN_APPROVED");
		map1.put("name", "未认证");
		list.add(map1);
		return new CertificateDataDetails("企业等级",list);
	}




}
