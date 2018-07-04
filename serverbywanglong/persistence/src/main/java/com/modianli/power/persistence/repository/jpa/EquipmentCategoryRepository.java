package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.EquipmentCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, Long> {

  EquipmentCategory findByNameAndCodeAndLevel(String name, String code, Integer level);

  EquipmentCategory findByNameAndCodeAndLevelAndParent(@Param("name") String name, @Param("code") String code,
													   @Param("level") Integer level, @Param("parent") EquipmentCategory e);

  List<EquipmentCategory> findByLevel(Integer level);

  List<EquipmentCategory> findByParent(@Param("parent") EquipmentCategory e);

  Page<EquipmentCategory> findByLevel(Integer level, Pageable pageable);

  EquipmentCategory findByIdAndLevel(@Param("id")Long id,@Param("level")Integer level);

  @Query("select e from EquipmentCategory e where "
         + "exists (select p from EquipmentCategory p where e.parent = p and p.parent = ?1) and e.hot = true ")
  List<EquipmentCategory> getHotThirdCategoryByFirst(EquipmentCategory e);

  List<EquipmentCategory> findByLevelAndHot(Integer level,Boolean hot);
}
