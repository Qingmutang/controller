package com.modianli.power.domain.es;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

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
public class EquipmentThirdCategoryEs implements Serializable {
  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String id;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String name;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String code;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String introduction;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.Integer)
  private Integer level;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.Boolean)
  private Boolean hot;

}
