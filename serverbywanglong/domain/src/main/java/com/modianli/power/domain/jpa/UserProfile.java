package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user_profiles")
@Access(AccessType.FIELD)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfile extends AuditableEntity<UserAccount, Long> {

  public enum CertificateType{
    UNAUTHORIZE, //未认证
    AUTHORIZING, //认证中
    AUTHORIZED, //认证成功
    REJECTED //认证被拒绝
  }

  private static final long serialVersionUID = 1L;

  //Date: 2017-3-2
  //By: zhanghaijun
  //start
  @Column(name = "head_image")
  private String headImage;//用户头像

  //end.

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserAccount account;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "certificate_status")
  private CertificateType certificateStatus;

  @OneToOne
  @JoinColumn(name = "user_certification_id")
  private UserCertification userCertification;

  @Column(name = "rejected_reason")
  private String rejectedReason;

}
