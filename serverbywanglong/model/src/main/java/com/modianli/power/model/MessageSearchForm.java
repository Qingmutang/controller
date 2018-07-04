package com.modianli.power.model;

import java.time.LocalDate;

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
public class MessageSearchForm {

  /**
   * 企业名称或手机号 查预算不传企业名称
   */
  private String content;
  /**
   * 创建时间
   */
  private LocalDate messageTime;
  /**
   * 留言类型，查预算传QUOTATION_BUDGET，查咨询合作传CONSULTING_COOPERATION
   */
  private  String messageType;
  /**
   * 省
   */
  private String province;
  /**
   * 市
   */
  private String city;
  /**
   * 需求类型 查预算传值，查咨询合作不传
   */
  private String requirementType;



}
