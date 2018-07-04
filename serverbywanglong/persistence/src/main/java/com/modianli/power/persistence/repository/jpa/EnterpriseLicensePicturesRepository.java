package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.EnterpriseLicensePictures;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by gao on 17-3-5.
 */
public interface EnterpriseLicensePicturesRepository extends JpaRepository<EnterpriseLicensePictures, Long>,
															 JpaSpecificationExecutor<EnterpriseLicensePictures>,
															 EnterpriseLicensePicturesRepositoryCustom{

  List<EnterpriseLicensePictures> findByEnterprise(@Param("enterprise") Enterprise enterprise);
}
