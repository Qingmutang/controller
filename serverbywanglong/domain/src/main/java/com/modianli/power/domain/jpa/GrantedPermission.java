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
 * @author hansy
 */

@Table(name = "granted_permissions")
@Entity
public class GrantedPermission extends PersistableEntity<Long> {

  @Column(name = "role_name")
  private String role;

  @Column(name = "permission")
  private String permission;

  public GrantedPermission() {
  }

  public GrantedPermission(String role, String permission) {
	this.role = role;
	this.permission = permission;
  }

  public String getRole() {
	return role;
  }

  public void setRole(String role) {
	this.role = role;
  }

  public String getPermission() {
	return permission;
  }

  public void setPermission(String permission) {
	this.permission = permission;
  }

  @Override
  public String toString() {
	return "GrantedPermission{" + "role=" + role + ", permission=" + permission + '}';
  }

}
