package com.modianli.power.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class UserProfileDetail implements Serializable {

  private Long id;

  private String userName;

  private String headImage;

  private String name;

  private String address;

  private String phone;

  private String contacts;

  private String qualificationCertificate;

  private String status;

  private String reason;

  private String logo;

  private Long provinceId;

  private Long cityId;

  private Long areaId;

  private LocalDateTime createdDate;

}
