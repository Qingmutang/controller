package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(//
	name = "staff_profiles")
@Access(AccessType.FIELD)
public class StaffProfile extends AuditableEntity<UserAccount, Long> {

  private static final long serialVersionUID = 1L;

  private String department;

  private String occupation;

  private String description;

  private String authentication;

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserAccount userAccount;

  public StaffProfile(String department, String occupation, String description, String authentication,
					  UserAccount userAccount) {
	this.department = department;
	this.occupation = occupation;
	this.description = description;
	this.authentication = authentication;
	this.userAccount = userAccount;
  }

  public StaffProfile() {
  }

  public String getDepartment() {
	return department;
  }

  public void setDepartment(String department) {
	this.department = department;
  }

  public String getOccupation() {
	return occupation;
  }

  public void setOccupation(String occupation) {
	this.occupation = occupation;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }

  public String getAuthentication() {
	return authentication;
  }

  public void setAuthentication(String authentication) {
	this.authentication = authentication;
  }

  public UserAccount getUserAccount() {
	return userAccount;
  }

  public void setUserAccount(UserAccount userAccount) {
	this.userAccount = userAccount;
  }

}
