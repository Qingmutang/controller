package com.modianli.power.model;

import java.io.Serializable;
import java.util.List;

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
public class RecommendEnterpriseSortForm implements Serializable {

  private static final long serialVersionUID = -2311550873855343493L;

  private List<IdAndSortValue> enterpriseSorts;

  private String type;

}
