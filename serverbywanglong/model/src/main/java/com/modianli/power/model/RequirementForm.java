package com.modianli.power.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by haijun on 2017/3/2.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequirementForm implements Serializable {

  private static final long serialVersionUID = 1L;

  //需求名称
  @NotBlank(message = "需求名称不能为空")
  @Length( min = 1, max = 100, message = "需求名称在5~100字之间")
  private String name;

  //需求描述
  @NotBlank(message = "需求描述不能为空")
  @Length( min = 1, max = 1000, message = "需求描述在10~1000字之间")
  private String description;

  //需求类型
  //@NotBlank(message = "服务类型不能为空")
  //private String categoryType;

  @NotNull(message = "服务类型不能为空")
  private List<Long> categoryIds;

  //截标日期
  @NotNull(message = "截标日期不能为空")
  private LocalDate biddingEndDate;

  //标书价格
  //@NotNull(message = "标书价格不能为空")
  private BigDecimal price;

  //标书资金来源
  @NotBlank(message = "标书资金来源不能为空")
  private String money;

  @NotBlank(message = "省份不能为空")
  private String provinceCode;

  @NotBlank(message = "省份名称不能为空")
  private String province;

  @NotBlank(message = "地区不能为空")
  private String areaCode;

  @NotBlank(message = "地区名称不能为空")
  private String area;

  @NotBlank(message = "市区不能为空")
  private String cityCode;

  @NotBlank(message = "市区名称不能为空")
  private String city;

  private String attachFile;
}
