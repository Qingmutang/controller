package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Product;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dell on 2017/3/3.
 */
public class ProductRepositoryImpl implements ProductRepositoryCustom {

  @PersistenceContext
  private EntityManager em;

  @Override
  @Transactional
  public void batchInsertCustom(List<Product> products) {
    for (int i=0;i<products.size();i++){
      Product product = products.get(i);
      product.setId(null);
      em.persist(product);
      if (i % 30 == 0){
        em.flush();
        em.clear();
	  }
	}
  }
}
