package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.DictionaryType;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DictionaryTypeRepository
	extends JpaBaseRepository<DictionaryType, Long>, JpaSpecificationExecutor<DictionaryType> {

  @Query("update DictionaryType as c set c.active=:active where c.id=:id ")
  @Modifying
  void updateActiveStatus(@Param("id") Long id, @Param("active") Boolean active);

  @Query("select  c from DictionaryType  as c where c.active=true and c.text like :text ")
  List<DictionaryType> findByText(@Param("text") String text);

}
