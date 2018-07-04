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
 * Created by dell on 2017/2/21.
 */
@Entity
@Table(name = "qualification_last_categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QualificationLastCategory extends PersistableEntity<Long> {

  @Column(name = "qualification_name", length = 50)
  private String name;

  @ManyToOne
  @JoinColumn(name = "qualification_middlecategory_id")
  private QualificationMiddleCategory qualificationMiddleCategory;


}
