package com.modianli.power.persistence.repository.es;

import com.modianli.power.domain.es.EnterpriseProductPriceEs;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by gao on 17-3-7.
 */
public interface EnterpriseProductPriceEsRepository extends ElasticsearchRepository<EnterpriseProductPriceEs, Long> {

}
