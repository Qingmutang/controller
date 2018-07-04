package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dictionary_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryType extends PersistableEntity<Long>  {

  private static final long serialVersionUID = 1198816304200812749L;

  @Column(name = "code")
  private String code;

  @Column(name = "is_active", nullable = false)
  private Boolean active=true;

  @Column(name = "text")
  private String text;


}
