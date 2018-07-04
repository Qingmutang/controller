package com.modianli.power.domain.es;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-3-6.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(indexName = "category_property_v1", type = "category", shards = 1, replicas = 0, refreshInterval = "-1")
public class EquipmentCategoryEs implements Serializable {

  private static final long serialVersionUID = 7033897068292418136L;
  @Id
  private String id;

  @Field(type = FieldType.Nested,index= FieldIndex.no)
  private List<EquipmentPropertyEs> categoryDetails;

}
