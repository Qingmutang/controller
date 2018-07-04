package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dell on 2017/2/21.
 */
@Entity
@Table(name = "certifications")
public class EnterpriseCertification extends AuditableEntity<UserAccount, Long> {



  @Column(name = "certification_type", length = 20)
  private Type type;

  @ManyToOne
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;

  public EnterpriseCertification() {
  }

  public EnterpriseCertification(Type type, Enterprise enterprise) {
    this.type = type;
    this.enterprise = enterprise;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Enterprise getEnterprise() {
    return enterprise;
  }

  public void setEnterprise(Enterprise enterprise) {
    this.enterprise = enterprise;
  }

  @Override
  public String toString() {
    return "EnterpriseCertification{" +
		   "type=" + type +
		   ", enterpriseBaseInfo=" + enterprise +
		   '}';
  }

  public enum Type {
    MODIANCERTIFICATION, // 魔电认证
    SHNAGHAIELECTRICPOWERASSOCIATIONCERTIFICATION, // 上海电力协会
  }


}
