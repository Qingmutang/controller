package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.CasePictures;
import com.modianli.power.domain.jpa.Cases;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by dell on 2017/2/28.
 */
public interface CasePicturesRepository extends JpaBaseRepository<CasePictures,Long>, JpaSpecificationExecutor<CasePictures>{

  List<CasePictures> findByCasesAndActive(Cases cases,Boolean active);
}
