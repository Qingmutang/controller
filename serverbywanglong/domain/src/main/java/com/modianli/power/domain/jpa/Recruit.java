package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-6-28.
 */
@Entity
@Table(name = "recruits")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Recruit extends AuditableEntity<UserAccount, Long> {

  private static final long serialVersionUID = 5647356226044511334L;

  @Column(name = "position_name", length = 50)
  private String positionName;

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

  @Column(name = "address", length = 200)
  private String address;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private DictionaryItem category;

  @Column(name = "description", length = 1000)
  private String description;

  @Column(name = "recruit_num")
  private Integer recruitNum;

  @Column(name = "low_salary")
  private Integer lowSalary;

  @Column(name = "high_salary")
  private Integer highSalary;

  @ManyToOne
  @JoinColumn(name = "experience_id")
  private DictionaryItem experience;

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserAccount userAccount;

  @Column(name = "mobile_number")
  private String mobileNumber;

  @Column(name = "is_active")
  private Boolean active = true;

  @Column(name = "tags", length = 200)
  private String tags;

  @Column(name = "uuid", length = 40)
  private String uuid;


}
