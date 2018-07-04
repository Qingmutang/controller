package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdAndSortValue implements Serializable{

  private static final long serialVersionUID = 3028184953941439056L;

  private Long id;

  private Integer sort;

}
