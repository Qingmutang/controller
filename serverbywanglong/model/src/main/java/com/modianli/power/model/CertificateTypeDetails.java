package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/4/26.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertificateTypeDetails implements Serializable {

  private String code;

  private String name;

  private String url;

  private Boolean active;

}
