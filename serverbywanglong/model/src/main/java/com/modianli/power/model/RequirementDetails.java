package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by haijun on 2017/3/2.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String requirementUUID;

  private String name;

  private String description;

  private String status;

  private LocalDateTime createdDate;

  private LocalDate pushDate;

  private LocalDate validateDate;

  private LocalDate matchDate;

  private LocalDate shelfDate;

  private String categoryType;

  private List<ServiceCategoryDetails> serviceCategoryDetailsList;

  private UserProfileDetail userProfile;

  private BigDecimal price;

  private LocalDate biddingEndDate;

  private String money;

  private boolean trusteeship;

  private Integer pageView;

  private String province;

  private String provinceCode;

  private String areaCode;

  private String area;

  private String cityCode;

  private String city;

  private String attachFile;
}
