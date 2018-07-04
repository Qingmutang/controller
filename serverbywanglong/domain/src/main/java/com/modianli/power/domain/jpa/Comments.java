package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

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
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments extends AuditableEntity<UserAccount, Long> {

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserAccount user;

  @Column(name = "description", length = 512, nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;

  @Column(name = "is_active", nullable = false)
  private Boolean active;
}

