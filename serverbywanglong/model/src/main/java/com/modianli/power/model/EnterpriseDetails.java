package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/24.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EnterpriseDetails implements Serializable{
  public enum Type {
	PERNAS, // 国企
	STOCKHOLDING_SYSTEM, // 股份制企业
	JOINT_VENTURE, // 合资企业
	LISTED_COMPANY, // 上市公司
	PRIVATE_ENTERPRISES, // 民营企业
	NGO;//民办非企业单位
  }

  private Long id;

  private String uuid;

  //企业名
  private String name;
  //社会统一信用
  private String creditCode;
  //注册号
  private String registrationID;
  //公司类型
  private Type type;
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
  private String email;
  //工作时间
  private String workTime;
  //图片地址
  private String imageUrl;
  //认证类型
  private String certificateType;

  //认证类型图片
  private String certificateTypeUrl;

  //企业描述
  private String enterpriseDescription;
  //营业执照
  private String licenseImageUrl;
  //省
  private String province;
  private String provinceCode;
  //市
  private String city;
  private String cityCode;
  //区
  private String area;
  private String areaCode;

  private List<CommentDetails> commentDetails;

  private List<EnterpriseQualificationDetails> enterpriseQualificationDetails;

  //安全生产许可证
  private String safetyProductionLicenseUrl;

  //资信等级证书
  private String creditGradeCertificateUrl;

  private List<EnterpriseLicensePicturesDetails> enterpriseLicensePictures;

  private List<Long> categoryIds;

  private String contacts;

  private List<Long> produceCategories;

}
