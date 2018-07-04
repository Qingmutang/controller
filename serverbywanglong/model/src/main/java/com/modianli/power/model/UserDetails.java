package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;

  private String name;

  private String mobileNumber;

  private BigDecimal amount;

  private BigDecimal fee;

  private LocalDate transactedDate;

  private String cardNumber;
}
