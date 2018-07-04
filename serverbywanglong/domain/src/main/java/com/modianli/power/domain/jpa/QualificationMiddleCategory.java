package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dell on 2017/2/21.
 */
@Entity
@Table(name = "qualification_middle_categories")
public class QualificationMiddleCategory extends PersistableEntity<Long> {

  private static final long serialVersionUID = 1L;
  @Column(name = "name",length = 50)
  private String name;

  @ManyToOne
  @JoinColumn(name = "qualification_top_category_id")
  private QualificationTopCategory qualificationTopCategory;

  public QualificationMiddleCategory() {
  }

  public QualificationMiddleCategory(String name, QualificationTopCategory qualificationTopCategory) {
    this.name = name;
    this.qualificationTopCategory = qualificationTopCategory;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public QualificationTopCategory getQualificationTopCategory() {
    return qualificationTopCategory;
  }

  public void setQualificationTopCategory(QualificationTopCategory qualificationTopCategory) {
    this.qualificationTopCategory = qualificationTopCategory;
  }

  @Override
  public String toString() {
    return "QualificationMiddleCategory{" +
        "name='" + name + '\'' +
        ", qualificationTopCategory=" + qualificationTopCategory +
        '}';
  }
}
