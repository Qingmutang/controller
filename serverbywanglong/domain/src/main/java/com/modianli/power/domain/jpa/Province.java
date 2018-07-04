/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author
 */
@Entity
@Table(name = "provinces")
public class Province extends PersistableEntity<Long> {

  private String name;

  @Column(name = "area_code")
  private String areaCode;

  public Province() {
  }

  public Province(String name, String areaCode) {
	this.name = name;
	this.areaCode = areaCode;
  }

  public Province(String name) {
	this.name = name;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public String getAreaCode() {
	return areaCode;
  }

  public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
  }


}
