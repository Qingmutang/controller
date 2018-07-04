package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.EnterpriseLicensePictures;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dell on 2017/3/9.
 */
public class EnterpriseLicensePicturesRepositoryImpl implements EnterpriseLicensePicturesRepositoryCustom{

  @PersistenceContext
  private EntityManager em;

  @Override
  public void batchSaveEnterpriseLicensePictures(List<EnterpriseLicensePictures> enterpriseLicensePictures) {
	for (int i=0;i<enterpriseLicensePictures.size();i++){
	  EnterpriseLicensePictures licensePictures = enterpriseLicensePictures.get(i);
	  licensePictures.setId(null);
	  em.persist(licensePictures);
	  if (i % 30 == 0){
		em.flush();
		em.clear();
	  }
	}
  }

  @Override
  public void batchDeleteEnterpriseLicensePictures(List<EnterpriseLicensePictures> enterpriseLicensePictures) {
	for (int i=0;i<enterpriseLicensePictures.size();i++){
	  EnterpriseLicensePictures licensePictures = enterpriseLicensePictures.get(i);
	  em.remove(em.contains(licensePictures) ? licensePictures : em.merge(licensePictures));
	  if (i % 30 == 0){
		em.flush();
		em.clear();
	  }
	}
  }
}
