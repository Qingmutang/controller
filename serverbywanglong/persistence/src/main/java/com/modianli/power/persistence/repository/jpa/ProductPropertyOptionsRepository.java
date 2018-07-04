package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Product;
import com.modianli.power.domain.jpa.ProductPropertyOptions;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductPropertyOptionsRepository extends JpaBaseRepository<ProductPropertyOptions, Long>,ProductPropertyOptionsRepositoryCustom {

  List<ProductPropertyOptions> findByProduct(@Param("product")Product product);
}
