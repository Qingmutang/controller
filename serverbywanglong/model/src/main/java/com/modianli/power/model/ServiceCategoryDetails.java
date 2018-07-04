package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/6/5.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCategoryDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String name;

  private String code;
}
