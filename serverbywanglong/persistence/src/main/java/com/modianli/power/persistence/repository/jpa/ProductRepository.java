package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>,ProductRepositoryCustom {

  Product findByName(String name);

  Page<Product> findByActiveOrderByCreatedDateDesc(Boolean active, Pageable pageable);

  Product findByCode(String code);

  @Query(value = "select a.id from EquipmentPropertyOptions a where exists ( select b.id from EquipmentProperty b where equipmentCategory.id = ?1 and a.equipmentProperty.id = b.id)")
  List<Long> getIdsByCategoryId(Long id);

  @Modifying
  @Query("update Product p set p.active =:active where p.id=:id")
  void updateActiveById(@Param("id") Long id, @Param("active") boolean active);

  List<Product> findByUuidIsNull();
}
