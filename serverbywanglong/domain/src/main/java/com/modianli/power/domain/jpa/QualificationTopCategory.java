package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dell on 2017/2/21.
 */

@Entity
@Table(name = "qualification_top_categories")
public class QualificationTopCategory extends PersistableEntity<Long> {

  private static final long serialVersionUID = 1L;
  @Column(name = "name",length = 50)
   private String name;

  public QualificationTopCategory() {
  }

  public QualificationTopCategory(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "QualificationTopCategory{" +
        "name='" + name + '\'' +
        '}';
  }
}
