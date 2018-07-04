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
 * Created by gao on 17-3-5.
 */
@Entity
@Table(name = "enterprise_license_pictures")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EnterpriseLicensePictures extends PersistableEntity<Long> {

  @Column(name = "url")
  private String url;

  @ManyToOne
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;
}



