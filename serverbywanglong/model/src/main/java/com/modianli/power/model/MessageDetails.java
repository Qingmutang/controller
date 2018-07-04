package com.modianli.power.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class MessageDetails implements Serializable{

  private Long id;
  private String messagePerson;

  private String messageDescription;

  private String name;

  private String phone;

  private LocalDate messageTime;

  private Long enterpriseId;

  private String city;

  private String province;

  private String requirementType;

  private LocalDateTime createdDate;

}
