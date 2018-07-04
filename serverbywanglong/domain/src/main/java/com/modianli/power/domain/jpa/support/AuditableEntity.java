package com.modianli.power.domain.jpa.support;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AuditableEntity<U, PK> extends PersistableEntity<PK> {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @CreatedBy
  @JoinColumn(name = "created_by")
  private U createdBy;

  @CreatedDate
  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @LastModifiedBy
  @JoinColumn(name = "last_modified_by")
  private U lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate;

  public U getCreatedBy() {
	return createdBy;
  }

  public void setCreatedBy(U createdBy) {
	this.createdBy = createdBy;
  }

  public LocalDateTime getCreatedDate() {
	return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
	this.createdDate = createdDate;
  }

  public U getLastModifiedBy() {
	return lastModifiedBy;
  }

  public void setLastModifiedBy(U lastModifiedBy) {
	this.lastModifiedBy = lastModifiedBy;
  }

  public LocalDateTime getLastModifiedDate() {
	return lastModifiedDate;
  }

  public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
	this.lastModifiedDate = lastModifiedDate;
  }

  @Override
  public String toString() {
	return "AuditableEntity{" + "createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedBy=" + lastModifiedBy
		   + ", lastModifiedDate=" + lastModifiedDate + '}';
  }


}
