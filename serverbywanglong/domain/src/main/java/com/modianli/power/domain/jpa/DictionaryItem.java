package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dictionary_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryItem extends PersistableEntity<Long> {

  private static final long serialVersionUID = 6213329170341733564L;

  @Column(name = "sort")
  private Integer sort;

  @Column(name = "text")
  private String text;

  @Column(name = "value")
  private String value;

  @ManyToOne
  @JoinColumn(name = "type_id")
  private DictionaryType dictionaryType;

  @Column(name = "is_active", nullable = false)
  private Boolean active = true;




}
