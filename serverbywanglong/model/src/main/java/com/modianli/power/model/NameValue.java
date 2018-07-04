package com.modianli.power.model;

import java.io.Serializable;

public class NameValue implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String name;

  public NameValue() {
  }

  public NameValue(Long id, String name) {
	this.id = id;
	this.name = name;
  }

  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  @Override
  public String toString() {
	return "NamedValue [id=" + id + ", name=" + name + "]";
  }

}
