package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.EquipmentProperty;
import com.modianli.power.domain.jpa.EquipmentPropertyOptions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipmentPropertyOptionsRepository extends JpaRepository<EquipmentPropertyOptions, Long> {

  List<EquipmentPropertyOptions> findByEquipmentProperty(@Param("equipmentProperty") EquipmentProperty equipmentProperty);

  EquipmentPropertyOptions findByCodeAndValueAndEquipmentProperty(@Param("code")String code,@Param("value")String value,@Param("equipmentProperty") EquipmentProperty equipmentProperty);

}
