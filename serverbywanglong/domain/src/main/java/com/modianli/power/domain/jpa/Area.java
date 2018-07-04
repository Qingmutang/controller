/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author
 */
@Entity
@Table(name = "areas")
public class Area extends PersistableEntity<Long> {

  @Column(name = "name")
  private String name;

  @Column(name = "area_code")
  private String areaCode;

  @ManyToOne
  @JoinColumn(name = "city_id")
  private City city;

  public Area() {
  }

  public Area(String name, String areaCode, City city) {
	this.name = name;
	this.areaCode = areaCode;
	this.city = city;
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

  public City getCity() {
	return city;
  }

  public void setCity(City city) {
	this.city = city;
  }
}
