package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Resource;

import java.util.List;

/**
 * Created by dell on 2017/3/9.
 */
public interface ResourceRepositoryCustom {
  void batchUpdateResource(List<Resource> resources);

}
