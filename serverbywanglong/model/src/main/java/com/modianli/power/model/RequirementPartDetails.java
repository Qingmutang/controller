package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementPartDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String requirementUUID;

  private String name;

  private String status;

  private LocalDate pushDate;

  private String categoryType;

  private BigDecimal price;
}
