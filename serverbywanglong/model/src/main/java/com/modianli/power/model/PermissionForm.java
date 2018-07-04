/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

/**
 * @author hansy
 */
public class PermissionForm {

  private String category;

  private String name;

  private String requestUri;

  private String requestMethod;

  private boolean active = true;

  private int position = 1;

  private String description;

  public String getCategory() {
	return category;
  }

  public void setCategory(String category) {
	this.category = category;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public String getRequestUri() {
	return requestUri;
  }

  public void setRequestUri(String requestUri) {
	this.requestUri = requestUri;
  }

  public String getRequestMethod() {
	return requestMethod;
  }

  public void setRequestMethod(String requestMethod) {
	this.requestMethod = requestMethod;
  }

  public boolean isActive() {
	return active;
  }

  public void setActive(boolean active) {
	this.active = active;
  }

  public int getPosition() {
	return position;
  }

  public void setPosition(int position) {
	this.position = position;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }

  @Override
  public String toString() {
	return "PermissionDetails{ category=" + category + ", name=" + name + ", requestUri=" + requestUri + ", requestMethod="
		   + requestMethod + ", active=" + active + ", position=" + position + ", description=" + description + '}';
  }

}
