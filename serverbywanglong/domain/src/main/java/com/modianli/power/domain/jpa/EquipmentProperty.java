/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author
 */
@Entity
@Table(name = "equipment_properties")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EquipmentProperty extends PersistableEntity<Long> {

  private String name;

  private String code;

  @ManyToOne
  @JoinColumn(name = "equipment_category_id")
  private EquipmentCategory equipmentCategory;


}
