package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dell on 2017/2/21.
 */
@Entity
@Table(name = "industry_categories")
public class IndustryCategory extends PersistableEntity<Long> {

  @Column(name = "category_name",length = 50)
  private String name;

  public IndustryCategory() {
  }

  public IndustryCategory(String name) {
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
    return "IndustryCategory{" +
        "name='" + name + '\'' +
        '}';
  }
}
