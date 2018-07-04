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
public class SystemUserForm implements Serializable {

  private static final long serialVersionUID = 1L;

  private String department;

  private String occupation;

  private String description;

  private String authentication;

  private UserForm userAccount;


}
