package com.modianli.power.persistence.repository.es;

import com.modianli.power.domain.es.EquipmentFirstCategoryEs;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by gao on 17-3-7.
 */
public interface EquipmentAllCategoryEsRepository extends ElasticsearchRepository<EquipmentFirstCategoryEs, String> {
}
