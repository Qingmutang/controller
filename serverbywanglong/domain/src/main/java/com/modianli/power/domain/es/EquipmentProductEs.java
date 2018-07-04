package com.modianli.power.domain.es;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Document(indexName = "product_v1", type = "product", shards = 1, replicas = 0, refreshInterval = "-1")
public class EquipmentProductEs implements Serializable {

  private static final long serialVersionUID = 7033897068292418136L;
  @Id
  private Long id;

  @Field(type =FieldType.String ,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
  private String name;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String pic;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String code;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.Boolean)
  private Boolean active;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.Nested)
  private List<EquipmentProductPropertyEs> info;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.Long)
  private Long firstCategoryId;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String firstCategoryName;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.Long)
  private Long secondCategoryId;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String secondCategoryName;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.Long)
  private Long thirdCategoryId;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String thirdCategoryName;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.Double)
  private BigDecimal defaultPrice;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String unit;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String uuid;

}
