package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.EnterpriseCount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by dell on 2017/2/23.
 */
public interface EnterpriseRepository
	extends JpaBaseRepository<Enterprise, Long>, JpaSpecificationExecutor<Enterprise> {

  Enterprise findByUuid(@Param("uuid") String uid);

  Enterprise findById(@Param("id") Long id);

  Enterprise findByName(String name);

  @Modifying
  @Query("update Enterprise enterprise set enterprise.active =:active where enterprise.id=:id")
  void deactivate(@Param("id") Long id, @Param("active") boolean active);

  Enterprise findByUserAccountId(Object object);

  @Query("select e from Enterprise e where not exists (select p from EnterpriseProductPrice p where p.enterprise = e and p.product.id=:id and p.active=true) and e.active=true and e.name like :name")
  List<Enterprise> getNoProductPriceEnterpriseByProductIdAndName(@Param("id") Long id, @Param("name") String name,
																 Pageable pageable);

  @Query("select e from Enterprise e where not exists (select p from EnterpriseProductPrice p where p.enterprise = e and p.product.id=:id and p.active=true) and e.active=true")
  List<Enterprise> getNoProductPriceEnterpriseByProductId(@Param("id") Long id, Pageable pageable);

  Page<Enterprise> findByActiveOrderByCreatedDateDesc(Boolean active, Pageable pageable);

  @Query("select new com.modianli.power.model.EnterpriseCount(count (e.id)  ,e.city,e.cityCode) from Enterprise e where e.active=true group by e.city,e.cityCode order by e.cityCode")
  List<EnterpriseCount> searchEnterpriseNumber();

  Long countByActive(@Param("active") Boolean active);

  Optional<Enterprise> findByUserAccount(@Param("userAccount") UserAccount userAccount);
}
