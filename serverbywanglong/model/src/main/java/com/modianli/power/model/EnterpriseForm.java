package com.modianli.power.model;

import org.hibernate.validator.constraints.Email;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnterpriseForm {

  //企业名
  private String name;
  //社会统一信用
  private String creditCode;
  //注册号
  private String registrationID;
  //公司类型
  private String type;
  //法人代表
  private String legalRepresentative;
  //注册资本
  private BigDecimal registeredCapital;
  //登记机关
  private String registrationAuthority;
  //组织机构代码
  private String organizingInstitutionCode;
  //经营状态
  private String managementForms;
  //成立日期
  private LocalDate foundDate;
  //营业期限
  private String operationPeriod;
  //发照日期
  private LocalDate issueDate;
  //公司地址
  private String enterpriseAddress;
  //商务电话
  private String businessPhone;
  //电话
  private String phone;
  //邮箱
  @Email(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式不正确")
  private String email;
  //工作时间
  private String workTime;
  //图片地址
  private String imageUrl;
  //企业描述
  private String enterpriseDescription;
  //营业执照
  private String licenseImageUrl;
  //认证类型
  private String certificateType;
  //省
  private String province;
  private String provinceCode;
  //市
  private String city;
  private String cityCode;
  //区
  private String area;
  private String areaCode;
  private List<EnterpriseQualificationForm> qualificationForms;

  private List<IdValue> categoryIds;

  //安全生产许可证
  private String safetyProductionLicenseUrl;

  //资信等级证书
  private String creditGradeCertificateUrl;

  private List<String> licensePictures;

  private String contacts;

  private List<IdValue> produceCategories;

}
