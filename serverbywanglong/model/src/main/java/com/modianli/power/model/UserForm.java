package com.modianli.power.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForm implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;

  private String password;

  private String name;

  private List<String> roles;

  private String mobileNumber;

  private String email;

//  private boolean active;

  private String gender;

//  private Long id;


}
