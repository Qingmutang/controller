package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by haijun on 2017/3/1.
 */
@Entity
@Table(name = "requirements")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Requirements extends AuditableEntity<UserAccount, Long> {

  public enum Status {
    CREATED,//需求创建
    VALIDATED,//审核通过
    BIDDING,//上架(竞标中)
    MATCHED,//匹配供应商
    BID,//选择供应商(中标)
    SERVED,//服务完成
    COMMENT,//评价
    FINISHED,//下架
    BE_FINISHED//截标
  }

  public enum CategoryType {
    CONSTRUCT,//施工
    DESIGN,//设计
    CONSTRUCT_DESIGN,//设计施工一体化
    SUPERVISE,//监理
    HR,//人力资源
    MATERIAL,//物资和设备采购
    CONSULT,//电力建设咨询
    OTHER//其他
  }

  @Column(name = "requirement_uuid")
  private String requirementUUID;//需求编号

  @ManyToOne
  @JoinColumn(name = "user_profile_id")
  private UserProfile userProfile;//发布需求的企业

  @Enumerated(EnumType.STRING)
  @Column(name = "category_type")
  private CategoryType categoryType;

  @Column(name = "name", length = 100)
  private String name;//需求名称

  @Column(name = "description", columnDefinition = "text")
  private String description;//需求描述

  @Column(name = "bidding_end_date", columnDefinition = "date")
  private LocalDate biddingEndDate;//竞标截止日期

  @Column(name = "push_date", columnDefinition = "date")
  private LocalDate pushDate;//发布需求时间

  @Column(name = "validate_date", columnDefinition = "date")
  private LocalDate validateDate;//审核通过时间

  @Column(name = "shelf_date", columnDefinition = "date")
  private LocalDate shelfDate;//上架时间

  @Column(name = "match_date", columnDefinition = "date")
  private LocalDate matchDate;//匹配供应商时间

  @Column(name = "finished_date", columnDefinition = "date")
  private LocalDate finishedDate;//下架时间

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @Column(name = "active")
  private boolean active = true;//逻辑上是否被删除， 1： 否； 0：是。

  @Column(name = "price")
  private BigDecimal price;//标书价格

  @Enumerated(EnumType.STRING)
  @Column(name = "money")
  private TYPE money;//资金来源

  public enum TYPE{
    financing,//融资
    self,//自有资金
    self_and_financing
  }

  @Column(name = "is_trusteeship")
  private boolean trusteeship = true;//是否托管

  @Column(name = "page_view")
  private Integer pageView = 0;//浏览次数

  @Column(name = "province_code")
  private String provinceCode;

  @Column(name = "province")
  private String province;

  @Column(name = "area_code")
  private String areaCode;

  @Column(name = "area")
  private String area;

  @Column(name = "city_code")
  private String cityCode;

  @Column(name = "city")
  private String city;

  @Column(name = "attach_file")
  private String attachFile;
}
