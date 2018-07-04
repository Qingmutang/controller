package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

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
@Table(name = "requirement_categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequirementCategory extends PersistableEntity<Long> {

  @ManyToOne()
  @JoinColumn(name = "service_category_id")
  private ServiceCategory serviceCategory;

  private Long requirementId;
}
