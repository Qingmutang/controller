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
public class HotProductSortForm implements Serializable {

  private static final long serialVersionUID = -4665637935138067102L;

  private List<IdAndSortValue> productSorts;

  private Long categoryId;

}
