package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by dell on 2017/4/24.
 */
@Data
@Builder
@AllArgsConstructor
public class EmailModel implements Serializable{

  private String username;

  private String enterpriseName;

  private String password;
}
