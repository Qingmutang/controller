package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import lombok.ToString;

import static com.modianli.power.domain.jpa.UserAccount.Gender.NA;
import static com.modianli.power.domain.jpa.UserAccount.Type.USER;

@Entity
@Table(//
	name = "users",//
	uniqueConstraints = {//

						 @UniqueConstraint(columnNames = {"username"})//
	}//
)
@Access(AccessType.FIELD)
@ToString
public class UserAccount extends AuditableEntity<UserAccount, Long> implements UserDetails {

  private static final long serialVersionUID = 1L;
  private String username;
  private String password;
  private String name;
  @Enumerated(EnumType.STRING)
  private Gender gender = NA;
  @Column(name = "mobile_number")
  private String mobileNumber;
  @Column(name = "email")
  private String email;
  @Column(name = "is_active")
  private boolean active = true;
  @Column(name = "account_type")
  @Enumerated(EnumType.STRING)
  private Type type = USER;
  @Column(name = "is_locked")
  private boolean locked = false;
  @Transient
  private Collection<? extends GrantedAuthority> authorities;

  @ElementCollection(fetch = FetchType.EAGER)
  @JoinTable(//
	  name = "user_roles", //
	  joinColumns = @JoinColumn(name = "user_id")//
  )
  private List<String> roles = new ArrayList<>();


  public UserAccount() {
  }

  public UserAccount(String username, String password, String name, boolean locked, UserAccount.Type type, String... roles) {
	super();
	this.username = username;
	this.password = password;
	this.name = name;
	this.roles = Arrays.asList(roles);
	this.locked = locked;
	this.type = type;
  }

  public boolean isUser(){
	return this.type.equals(Type.USER);
  }

  public boolean isStaff(){
	return this.type.equals(Type.STAFF);
  }

  public boolean isEnterprise(){
	return this.type.equals(Type.ENTERPRISE);
  }

  public synchronized void addRole(String r) {
	if (!this.roles.contains(r)) {
	  this.roles.add(r);
	}
  }

  public synchronized void removeRole(String r) {
	if (this.roles.contains(r)) {
	  this.roles.remove(r);
	}
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public Gender getGender() {
	return gender;
  }

  public void setGender(Gender gender) {
	this.gender = gender;
  }

  public String getMobileNumber() {
	return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
  }

  public String getEmail() {
	return email;
  }

  public void setEmail(String email) {
	this.email = email;
  }

  public boolean isActive() {
	return active;
  }

  public void setActive(boolean active) {
	this.active = active;
  }

  public synchronized List<String> getRoles() {
	return roles;
  }

  public synchronized void setRoles(List<String> roles) {
	this.roles = roles;
  }

  public Type getType() {
	return type;
  }

  public void setType(Type type) {
	this.type = type;
  }

  public boolean hasUserRole() {
	for (String r : this.getRoles()) {
	  if ("USER".equals(r)) {
		return true;
	  }
	}
	return false;
  }

  public boolean hasRole(String type){
    if(StringUtils.isBlank(type)){
      return false;
	}

	for (String r : this.getRoles()) {
	  if (type.equals(r)) {
		return true;
	  }
	}
	return false;
  }

  @Override
  public String getUsername() {
	return username;
  }

  public void setUsername(String username) {
	this.username = username;
  }

  @Override
  public String getPassword() {
	return password;
  }

  public void setPassword(String password) {
	this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
	return this.authorities;
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
	this.authorities = authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
	return true;
  }

  @Override
  public boolean isAccountNonLocked() {
	return (!this.locked);
  }

  @Override
  public boolean isCredentialsNonExpired() {
	return true;
  }

  @Override
  public boolean isEnabled() {
	return this.active;
  }

  public boolean isLocked() {
	return locked;
  }

  public void setLocked(boolean locked) {
	this.locked = locked;
  }

  public enum Gender {

	NA, //
	MALE, //
	FAMALE;//
  }

  public enum Type {

	USER, //
	ENTERPRISE, //
	STAFF//
  }

}
