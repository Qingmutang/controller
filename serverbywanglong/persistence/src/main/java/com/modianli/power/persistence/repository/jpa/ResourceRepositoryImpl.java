package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dell on 2017/4/6.
 */
public class ResourceRepositoryImpl implements ResourceRepositoryCustom{

  @PersistenceContext
  protected EntityManager em;

  @Override
  public void batchUpdateResource(List<Resource> resources) {
	for (int i = 0; i < resources.size(); i++) {
	  em.merge(resources.get(i));
	  if (i % 30 == 0){
		em.flush();
		em.clear();
	  }
	}
  }
}
