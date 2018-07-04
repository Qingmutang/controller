package com.modianli.power.model;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/28.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCertificationForm implements Serializable{
  @NotBlank
  private String logo;

  @NotBlank
  @Size(max = 255, message = "名称长度过长")
  private String name;

  @NotBlank
  @Size(max = 255, message = "地址长度过长")
  private String address;

  @NotBlank
  private String phone;

  @NotBlank
  private String contacts;

  @NotBlank
  private String qualificationCertificate;

  @NotNull
  private Long provinceId;

  @NotNull
  private Long cityId;

  @NotNull
  private Long areaId;
}
