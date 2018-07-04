package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by dell on 2017/2/24.
 */
@Entity
@Table(name = "enterprise_qualification")
public class EnterpriseQualification extends PersistableEntity<Long>{


  @OneToOne
  @JoinColumn(name = "qualification_last_category_id")
  private QualificationLastCategory qualificationLastCategory;

  @ManyToOne
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;

  public EnterpriseQualification() {

  }

  public EnterpriseQualification(QualificationLastCategory qualificationLastCategory,
								 Enterprise enterprise) {
    this.qualificationLastCategory = qualificationLastCategory;
    this.enterprise = enterprise;
  }

  public QualificationLastCategory getQualificationLastCategory() {
    return qualificationLastCategory;
  }

  public void setQualificationLastCategory(QualificationLastCategory qualificationLastCategory) {
    this.qualificationLastCategory = qualificationLastCategory;
  }

  public Enterprise getEnterprise() {
    return enterprise;
  }

  public void setEnterprise(Enterprise enterprise) {
    this.enterprise = enterprise;
  }

  @Override
  public String toString() {
    return "EnterpriseQualification{" +
		   "qualificationLastCategory=" + qualificationLastCategory +
		   ", enterpriseBaseInfo=" + enterprise +
		   '}';
  }
}
