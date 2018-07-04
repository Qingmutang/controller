package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.EquipmentCategoryDetails;
import com.modianli.power.domain.jpa.EquipmentCategory;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dell on 2017/2/27.
 */
public interface EquipmentCategoryDetailsRepository
	extends JpaBaseRepository<EquipmentCategoryDetails,Long>, JpaSpecificationExecutor<EquipmentCategoryDetails> {


  EquipmentCategoryDetails findByCategory(EquipmentCategory category);
}
