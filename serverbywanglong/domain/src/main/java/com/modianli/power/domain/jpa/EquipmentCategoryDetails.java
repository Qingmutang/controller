package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/21.
 */
@Entity
@Table(name = "equipment_category_details")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EquipmentCategoryDetails extends PersistableEntity<Long> {

  @Column(name = "unit")
  private String unit;


  @OneToOne
  @JoinColumn(name = "category_id")
  private EquipmentCategory category;
}
