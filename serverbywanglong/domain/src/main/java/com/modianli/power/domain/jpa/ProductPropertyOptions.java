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
 * Created by gao on 17-2-23.
 */
@Entity
@Table(name = "product_property_options")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductPropertyOptions extends PersistableEntity<Long> {

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "equipment_property_options_id")
  private EquipmentPropertyOptions equipmentPropertyOptions;

  @Column(name = "equipment_property_name")
  private String equipmentPropertyName;

  @Column(name = "equipment_property_options_name")
  private String equipmentPropertyOptionsName;


}
