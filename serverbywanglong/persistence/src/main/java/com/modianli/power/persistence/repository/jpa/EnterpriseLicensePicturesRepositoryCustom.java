package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.EnterpriseLicensePictures;

import java.util.List;

/**
 * Created by dell on 2017/3/9.
 */
public interface EnterpriseLicensePicturesRepositoryCustom {
  void batchSaveEnterpriseLicensePictures(List<EnterpriseLicensePictures> enterpriseLicensePictures);

  void batchDeleteEnterpriseLicensePictures(List<EnterpriseLicensePictures> enterpriseLicensePictures);

}
