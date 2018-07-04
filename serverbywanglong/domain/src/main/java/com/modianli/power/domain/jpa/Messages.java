package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;
import com.modianli.power.model.enums.MessageType;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Messages extends AuditableEntity<UserAccount, Long> {
//  public enum MessageType{
//    CONSULTING_COOPERATION, //咨询合作
//    QUOTATION_BUDGET  //申请报价预算
//  }

  @Column(name = "message_person", length = 100)
  private String messagePerson;

  @Column(name = "message_description", length = 100)
  private String messageDescription;

  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "phone", length = 100, nullable = false)
  private String phone;

  @Column(name = "message_time", length = 100)
  private LocalDate messageTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "message_type", length = 100)
  private MessageType type;

  @Column(name = "province",length = 50)
  private String province;

  @Column(name = "city",length = 50)
  private String city;

  @Column(name = "requirement_type", length = 100)
  private String requirementType;

  @ManyToOne
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;
}
