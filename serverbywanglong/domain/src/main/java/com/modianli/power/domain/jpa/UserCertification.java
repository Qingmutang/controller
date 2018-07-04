package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/21.
 */
@Entity
@Table(name = "user_certification")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCertification extends AuditableEntity<UserAccount, Long> {

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private String address;

  @Column(name = "phone")
  private String phone;

  @Column(name = "contacts")
  private String contacts;

  @Column(name = "qualification_certificate")
  private String qualificationCertificate;

  @Column(name = "province_id")
  private Long provinceId;

  @Column(name = "city_id")
  private Long cityId;

  @Column(name = "area_id")
  private Long areaId;

  @Column(name = "area_code")
  private String areaCode;

  @Column(name = "logo")
  private String logo;//企业logo

}
