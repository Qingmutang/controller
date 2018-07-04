package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/5/17.
 */
@Entity
@Table(name = "recommend_enterprise")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecommendEnterprise extends PersistableEntity<Long> {

  public enum Type{
    DEFAULT_HOME_PAGE,ENTERPRISE_HOME_PAGE
  }

  @OneToOne
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;

  @Column(name = "is_active")
  private Boolean active=true;

  @Column(name = "sort",length = 20)
  private Integer sort;

  @Enumerated(EnumType.STRING)
  @Column(name = "type",length = 20)
  private Type type;
}
