/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.common.exception;

/**
 * @author
 */
public class RoleNameExistedException extends RuntimeException {

  private String roleName;

  public RoleNameExistedException(String roleName) {
	this.roleName = roleName;
  }

  public String getRoleName() {
	return roleName;
  }

  public void setRoleName(String roleName) {
	this.roleName = roleName;
  }

}
