package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dell on 2017/2/21.
 */
@Entity
@Table(name = "enterprise_categories")
public class EnterpriseCategory extends PersistableEntity<Long> {


  @ManyToOne
  @JoinColumn(name = "industry_category_id")
  private IndustryCategory industryCategory;


  @ManyToOne
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;

  public EnterpriseCategory() {
  }

  public EnterpriseCategory(IndustryCategory industryCategory, Enterprise enterprise) {
    this.industryCategory = industryCategory;
    this.enterprise = enterprise;
  }

  public IndustryCategory getIndustryCategory() {
    return industryCategory;
  }

  public void setIndustryCategory(IndustryCategory industryCategory) {
    this.industryCategory = industryCategory;
  }

  public Enterprise getEnterprise() {
    return enterprise;
  }

  public void setEnterprise(Enterprise enterprise) {
    this.enterprise = enterprise;
  }

  @Override
  public String toString() {
    return "EnterpriseCategory{" +
		   "industryCategory=" + industryCategory +
		   ", enterpriseBaseInfo=" + enterprise +
		   '}';
  }
}
