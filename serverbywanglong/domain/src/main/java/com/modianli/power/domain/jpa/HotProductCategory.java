package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-2-23.
 */
@Entity
@Table(name = "hot_product_category")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotProductCategory extends PersistableEntity<Long> {
  public enum Type{
    DEFAULT_HOME_PAGE,MODIAN_HOME_PAGE
  }

  @Column(name = "name",length = 20)
  private String name;

  @Column(name = "url",length = 256)
  private String url;

  @Column(name = "sort",length = 20)
  private Integer sort;

  @Column(name = "is_active")
  private Boolean active=true;

  @Enumerated(EnumType.STRING)
  @Column(name = "type",length = 20)
  private Type type;
}
