package com.modianli.power.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/5/23.
 */
@Data
@Builder
@NoArgsConstructor

public class EnterpriseCount implements Serializable {

  private static final long serialVersionUID = -2136871524581767330L;
  private Long count;
  private String city;
  private String code;

  public EnterpriseCount(Long count, String city, String code) {
	this.code = code;
	this.city = city;
	this.count = count;
  }
}
