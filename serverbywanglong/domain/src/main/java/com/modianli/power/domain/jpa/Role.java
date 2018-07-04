package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	name = "roles",
	uniqueConstraints = {//

						 @UniqueConstraint(columnNames = {"name"})//
	}//
)
@Access(AccessType.FIELD)
public class Role extends PersistableEntity<Long> implements GrantedAuthority {

  //reserved roles, ADMIN and USER can not be edited and deleted.
  public static final String ROLE_ADMIN = "ADMIN";
  public static final String ROLE_USER = "USER";
  public static final String ROLE_ENTERPRISE = "ENTERPRISE";

  private static final long serialVersionUID = 1L;
  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "is_active")
  private boolean active = true;

  public Role(String name) {
	this.name = name;
  }

  public Role() {
  }

  @Override
  public String getAuthority() {
	return "ROLE_" + this.name;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }

  public boolean isActive() {
	return active;
  }

  public void setActive(boolean active) {
	this.active = active;
  }

  @Override
  public int hashCode() {
	int hash = 3;
	hash = 11 * hash + Objects.hashCode(this.name);
	return hash;
  }

  @Override
  public boolean equals(Object obj) {
	if (obj == null) {
	  return false;
	}
	if (getClass() != obj.getClass()) {
	  return false;
	}
	final Role other = (Role) obj;
	if (!Objects.equals(this.name, other.name)) {
	  return false;
	}
	return true;
  }

  @Override
  public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
