package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import java.time.LocalDate;

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
 * Created by dell on 2017/2/21.
 */
@Entity
@Table(name = "cases")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cases extends AuditableEntity<UserAccount, Long> {

  @Column(name = "project_name", length = 100, nullable = false)
  private String projectName;

  @Column(name = "project_description", length = 100, nullable = false)
  private String projectDescription;

  @Column(name = "project_time", nullable = false)
  private LocalDate projectTime;

  @ManyToOne
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;

  @Column(name = "is_active")
  private Boolean active = true;
}
