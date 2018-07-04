package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/4/26.
 */
@Entity
@Table(name = "certificate_types")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CertificateType extends PersistableEntity<Long> {

  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;

  @Column(name = "url")
  private String url;

  @Column(name = "is_active")
  private Boolean active;

}
