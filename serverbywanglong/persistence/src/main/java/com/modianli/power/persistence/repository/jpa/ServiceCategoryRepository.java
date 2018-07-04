package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.ServiceCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by haijun on 2017/6/5.
 */
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long>,//
												   JpaSpecificationExecutor<ServiceCategory> {

  List<ServiceCategory> findByParentCategory(ServiceCategory serviceCategory);

  ServiceCategory findByCode(String code);
}
