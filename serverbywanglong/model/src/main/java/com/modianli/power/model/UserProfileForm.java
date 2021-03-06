package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/3/29.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileForm implements Serializable {
  private String headImage;

  private String name;

  private String address;

  private String phone;

  private String contacts;

  private String qualificationCertificate;

}
