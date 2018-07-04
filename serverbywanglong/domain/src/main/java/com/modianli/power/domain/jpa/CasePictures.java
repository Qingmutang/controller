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

/**
 * Created by dell on 2017/2/21.
 */
@Entity
@Table(name = "case_pictures")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CasePictures extends PersistableEntity<Long> {

  @Column(name = "url", length = 100, nullable = false)
  private String url;

  @ManyToOne
  @JoinColumn(name = "case_id")
  private Cases cases;

  @Column(name = "is_active")
  private Boolean active=true;
}
