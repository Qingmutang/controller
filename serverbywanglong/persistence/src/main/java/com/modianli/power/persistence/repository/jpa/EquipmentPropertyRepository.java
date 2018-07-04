package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.EquipmentCategory;
import com.modianli.power.domain.jpa.EquipmentProperty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipmentPropertyRepository extends JpaRepository<EquipmentProperty, Long> {

  List<EquipmentProperty> findByEquipmentCategory(@Param("equipmentCategory") EquipmentCategory equipmentCategory);

  EquipmentProperty findByCodeAndNameAndEquipmentCategory(@Param("code")String code,@Param("name")String name,@Param("equipmentCategory") EquipmentCategory equipmentCategory);

}
