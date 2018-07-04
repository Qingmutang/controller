/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author
 */
@Entity
@Table(name = "equipment_categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EquipmentCategory extends PersistableEntity<Long> {

  private String name;

  @Column(name = "code",length = 50)
  private String code;

  @Column(name = "introduction")
  private String introduction;

  @ManyToOne()
  @JoinColumn(name = "parent_id")
  private EquipmentCategory parent;

  @Column(name = "level",length = 2)
  private Integer level;

  @Column(name = "is_hot")
  private Boolean hot = false;

}
