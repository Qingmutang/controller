package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by haijun on 2017/6/5.
 */
@Entity
@Table(name = "service_categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ServiceCategory extends PersistableEntity<Long> {

  @Column(name = "name", length = 50)
  private String name;

  @Column(name = "code", length = 50)
  private String code;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private ServiceCategory parentCategory;
}
