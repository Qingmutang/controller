package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.EnterpriseProductPrice;
import com.modianli.power.domain.jpa.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dell on 2017/2/23.
 */
public interface EnterpriseProductPriceRepository
	extends JpaBaseRepository<EnterpriseProductPrice, Long>, JpaSpecificationExecutor<EnterpriseProductPrice> {

  @Query("select count(e) from EnterpriseProductPrice e where e.product.id = ?1 and e.active=true")
  Long getEnterpriseProductPriceCount(Long productId);

  @Query("select e from EnterpriseProductPrice e ,Enterprise p where e.enterprise = p and e.product.uuid = ?1 and p.active=true and e.active=true")
  Page<EnterpriseProductPrice> getActiveEnterpriseProductPrice(String uuid, Pageable page);

  @Query("select e from EnterpriseProductPrice e ,Enterprise p where e.enterprise = p and e.product.id = ?1 and p.active=true and e.active=true")
  Page<EnterpriseProductPrice> getActiveEnterpriseProductPrice(Long id, Pageable page);

  EnterpriseProductPrice findTop1ByProductOrderByPriceDate(Product product);

  EnterpriseProductPrice findByProductAndEnterprise(Product product, Enterprise enterprise);

  List<EnterpriseProductPrice> findByEnterprise(Enterprise enterprise);

  @Query("select e from EnterpriseProductPrice e ,Enterprise p where e.enterprise = p and p.active=true and e.active=true group by e.product")
  List<EnterpriseProductPrice> getAllProductPriceGroupByProduct();


}
