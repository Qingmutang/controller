package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.DictionaryItem;
import com.modianli.power.domain.jpa.DictionaryType;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dell on 2017/2/28.
 */
public interface DictionaryItemRepository
	extends JpaBaseRepository<DictionaryItem, Long>, JpaSpecificationExecutor<DictionaryItem> {

  @Query("select di from DictionaryItem as di inner join di.dictionaryType as dt where dt.code = :code  ")
  List<DictionaryItem> findByCode(@Param("code") String code, Sort sort);

  @Query("select di from DictionaryItem as di inner join di.dictionaryType as dt where dt.code = :code and di.active=true ")
  List<DictionaryItem> findByCodeAndActive(@Param("code") String code, Sort sort);

  @Query("select di from DictionaryItem as di where di.value in :values")
  List<DictionaryItem> findByCodes(@Param("values") List<String> values);

  @Query("select max(di.sort)  from DictionaryItem  as di where di.dictionaryType=:dictionaryType and di.active=true ")
  Long findMaxSortByDictionaryType(@Param("dictionaryType") DictionaryType dictionaryType);

  @Query("update DictionaryItem as c set c.active=:active where c.id=:id ")
  @Modifying
  void updateActiveStatus(@Param("id") Long id, @Param("active") Boolean active);
}
