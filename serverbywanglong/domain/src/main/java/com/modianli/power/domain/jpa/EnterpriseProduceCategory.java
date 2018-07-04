package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enterprise_produce_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnterpriseProduceCategory extends AuditableEntity<UserAccount, Long> {

  @ManyToOne
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private DictionaryItem dictionaryItem;

  @Column(name = "is_active", nullable = false)
  private Boolean active = true;

}
