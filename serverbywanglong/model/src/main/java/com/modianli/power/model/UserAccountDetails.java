package com.modianli.power.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  //    private String password;
  private String name;

  private String gender;

  private String mobileNumber;

  private String email;

  private boolean active = true;

  private boolean locked = false;

  private LocalDateTime createdDate;



  private LocalDateTime mobileNumberVerificationVerifiedDate;

  private List<String> roles;



  private UserProfileDetail userProfileDetail;


}
