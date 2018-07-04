package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/25.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseQualificationDetails implements Serializable {

  private Long topCateGoryId;

  private String topCateGoryName;

  private Long middleCateGoryId;

  private String middleCateGoryName;

  private Long lastCateGoryId;

  private String lastCateGoryName;

}
