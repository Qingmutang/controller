package com.modianli.power.model;

import com.modianli.power.model.enums.MessageType;
import com.modianli.power.model.validator.StringEnumeration;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/28.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageForm {

  private String messagePerson;

  private String messageDescription;

  private String name;

  private String phone;

  private String city;

  private String province;

  private LocalDate messageTime;

  private Long enterpriseId;

  @StringEnumeration(enumClass = MessageType.class)
  private String type;

  private String requirementType;


}
