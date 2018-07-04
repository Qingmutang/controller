package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.ProductPropertyOptions;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dell on 2017/3/8.
 */
public class ProductPropertyOptionsRepositoryImpl implements ProductPropertyOptionsRepositoryCustom{

  @PersistenceContext
  protected EntityManager em;

  @Override
  @Transactional
  public void batchSaveProductPropertyOptions(List<ProductPropertyOptions> propertyOptions) {
	for (int i = 0; i < propertyOptions.size(); i++) {
	  em.persist(propertyOptions.get(i));
	  if (i % 30 == 0){
	    em.flush();
		em.clear();
	  }
	}

  }
}
