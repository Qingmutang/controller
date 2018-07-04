package com.modianli.power.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/25.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualificationTopDetails {
  private static final long serialVersionUID = 123725354963534361L;
  private Long id;
  private String name;

}
