package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Product;

import java.util.List;

/**
 * Created by dell on 2017/3/3.
 */
public interface ProductRepositoryCustom {

  void batchInsertCustom(List<Product> products);
}
