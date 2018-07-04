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
public class UserProfileDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private String secondaryEmail;

  private String secondaryMobileNumber;

  private String instantMessager;

  private String headImage;

  private EnterpriseDetails enterprise;

  private UserAccountDetails userAccount;
}
