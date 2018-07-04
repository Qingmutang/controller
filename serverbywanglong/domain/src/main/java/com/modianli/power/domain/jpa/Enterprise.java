package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/21.
 */
@Entity
@Table(name = "enterprises")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Enterprise extends AuditableEntity<UserAccount, Long> {

  private static final long serialVersionUID = 1L;

  public enum Type {
	PERNAS, // 国企
	STOCKHOLDING_SYSTEM, // 股份制企业
	JOINT_VENTURE, // 合资企业
	LISTED_COMPANY, // 上市公司
	PRIVATE_ENTERPRISES, // 民营企业
	NGO;//民办非企业单位
  }

//  public enums CertificateType {
//
//	MODIANCERTIFICATION, // 魔电认证
//	SHNAGHAI_ELECTRIC_POWER_ASSOCIATION_CERTIFICATION, // 上海电力协会
//    NONE, // 未认证
//  }

  @Column(name = "uuid", length = 36, nullable = true)
  private String uuid;

  @Column(name = "name", length = 100, nullable = true)
  private String name;

  @Column(name = "soc_credit_code", length = 100, nullable = true)
  private String creditCode;

  @Column(name = "register_id", length = 100, nullable = true)
  private String registrationID;

  @Enumerated(EnumType.STRING)
  @Column(name = "enterprise_type", length = 20)
  private Type type;

  @Column(name = "legal_representative", length = 100, nullable = true)
  private String legalRepresentative;

  @Column(name = "registered_capital", length = 50, scale = 6)
  private BigDecimal registeredCapital;

  @Column(name = "registration_authority", length = 50, nullable = true)
  private String registrationAuthority;

  @Column(name = "org_institution_code", length = 50, nullable = true)
  private String organizingInstitutionCode;

  @Column(name = "management_forms", length = 50, nullable = true)
  private String managementForms;

  @Column(name = "found_date", nullable = true)
  private LocalDate foundDate;

  @Column(name = "operation_period", length = 50, nullable = true)
  private String operationPeriod;

  @Column(name = "issue_date", nullable = true)
  private LocalDate issueDate;

  @Column(name = "enterprise_address", length = 200, nullable = true)
  private String enterpriseAddress;

  @Column(name = "business_phone", length = 50, nullable = true)
  private String businessPhone;

  @Column(name = "phone", length = 50, nullable = true)
  private String phone;

  @Column(name = "email", length = 50, nullable = true)
  private String email;

  @Column(name = "work_time", length = 50, nullable = true)
  private String workTime;

  @Column(name = "image_url", length = 100, nullable = true)
  private String imageUrl;

  @Column(name = "certificate_type",length = 50,nullable = true)
  private String certificateType;

  @Column(name = "province", length = 50, nullable = true)
  private String province;

  @Column(name = "province_code", length = 50)
  private String provinceCode;

  @Column(name = "city", length = 50, nullable = true)
  private String city;

  @Column(name = "city_code", length = 50, nullable = true)
  private String cityCode;

  @Column(name = "area", length = 50, nullable = true)
  private String area;

  @Column(name = "area_code", length = 50)
  private String areaCode;

  @Column(name = "enterprise_description", columnDefinition = "text")
  private String enterpriseDescription;

  @Column(name = "license_image_url", length = 100)
  private String licenseImageUrl;

  @Column(name = "is_active")
  private Boolean active = true;

  //安全生产许可证
  @Column(name = "safety_production_license_url")
  private String safetyProductionLicenseUrl;

  //资信等级证书
  @Column(name = "credit_grade_certificate_url")
  private String creditGradeCertificateUrl;

  @Column(name = "contacts")
  private String contacts;

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserAccount userAccount;

  @Transient
  private String username;

  public String getUsername(){
    if (null != this.userAccount){
      return this.userAccount.getUsername();
    }
    return null;
  }
//  //核承压设备安装资质许可证
//  @Column(name = "safety_production_license_url")
//  private String safetyProductionLicenseUrl;

//  //合同信用等级认定证书
//  @Column(name = "credit_grade_certificate_url")
//  private String creditGradeCertificateUrl;


}
