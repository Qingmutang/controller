package com.modianli.power.persistence.repository.es;

import com.modianli.power.domain.es.EquipmentCategoryEs;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by gao on 17-3-7.
 */
public interface EquipmentCategoryEsRepository extends ElasticsearchRepository<EquipmentCategoryEs, String> {
}
