/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDetails {

  private Long id;

  private String category;

  private String name;

  private String requestUri;

  private String requestMethod;

  private boolean active = true;

  private int position = 1;

  private String description;


}
