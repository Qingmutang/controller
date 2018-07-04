/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 */
@Entity
@Table(name = "resource")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource extends PersistableEntity<Long> {

  @Column(name = "url")
  private String url;

  @Column(name = "is_delete")
  private Boolean deleteStatus;

  @Column(name = "is_active")
  private Boolean active;

  @Column(name = "create_date")
  private LocalDateTime createDate;
}
