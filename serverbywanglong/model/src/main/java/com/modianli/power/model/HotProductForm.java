package com.modianli.power.model;

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
public class HotProductForm {

  private Long productId;

  private Long categoryId;

}
