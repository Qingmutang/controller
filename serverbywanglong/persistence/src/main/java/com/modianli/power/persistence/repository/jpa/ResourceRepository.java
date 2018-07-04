package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource>,ResourceRepositoryCustom{

  Resource findByUrl(String url);

  List<Resource> findByActiveAndDeleteStatus(Boolean active, Boolean delete);
}
