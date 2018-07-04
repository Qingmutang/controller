package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String name;

  private String description;

  private boolean active;


}
