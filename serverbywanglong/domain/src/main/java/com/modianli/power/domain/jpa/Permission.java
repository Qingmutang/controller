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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	name = "permissions",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"name"})
	}
)
@Access(AccessType.FIELD)
public class Permission extends PersistableEntity<Long> implements GrantedAuthority {

  private static final long serialVersionUID = 1L;

  private static final String PERM_VIEW_BANK = "PERM_VIEW_BANK";

  private static final String PERM_ADD_BANK = "PERM_ADD_BANK";

  private static final String PERM_EDIT_BANK = "PERM_EDIT_BANK";

  private static final String PERM_DELETE_BANK = "PERM_DELETE_BANK";
  @Enumerated(EnumType.STRING)
  private Category category = Category.DEFAULT;
  private String name;
  @Column(name = "request_uri")
  private String requestUri;
  @Column(name = "request_method")
  private String requestMethod;
  @Column(name = "is_active")
  private boolean active = true;
  private int position = 1;
  @Column(name = "description")
  private String description;

  public Permission() {
  }

  public Permission(
	  Category category,
	  String name,
	  String requestUri,
	  String requestMethod,
	  int position) {
	this.category = category;
	this.name = name;
	this.requestUri = requestUri;
	this.requestMethod = requestMethod;
	this.position = position;
  }

  @Override
  public String getAuthority() {
	return this.name;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public String getRequestMethod() {
	return requestMethod;
  }

  public void setRequestMethod(String requestMethod) {
	this.requestMethod = requestMethod;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }

  public String getRequestUri() {
	return requestUri;
  }

  public void setRequestUri(String requestUri) {
	this.requestUri = requestUri;
  }

  public Category getCategory() {
	return category;
  }

  public void setCategory(Category category) {
	this.category = category;
  }

  public boolean isActive() {
	return active;
  }

  public void setActive(boolean active) {
	this.active = active;
  }

  public Integer getPosition() {
	return position;
  }

  public void setPosition(Integer position) {
	this.position = position;
  }

  @Override
  public int hashCode() {
	int hash = 3;
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
	final Permission other = (Permission) obj;
	return Objects.equals(this.name, other.name);
  }

  @Override
  public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public enum Category {

	BANK,//
	QINIU,//
	ALIASES,//
	ENTERPRISE,//
	EQUIPMENT,//
	BILL,
	PRODUCT,//
	DEFAULT, //
	ORDER, //
	USER,
	CONTENT_POST, //
	ACCOUNT, //
	ACCOUNTING, //
	CONFIG,
	REPORT,
	REQUIREMENT,
	RECRUIT,
	DICTIONARY
  }

}
