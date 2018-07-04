package com.modianli.power.domain.es;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-3-7.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EquipmentPropertyEs implements Serializable{

  @Field(index = FieldIndex.not_analyzed,type = FieldType.Long)
  private Long id;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String name;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.String)
  private String code;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.Nested)
  private List<EquipmentPropertyOptionsEs> propertyOptionsEs;
}
