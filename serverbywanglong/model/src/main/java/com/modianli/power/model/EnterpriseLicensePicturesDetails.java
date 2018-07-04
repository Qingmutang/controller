package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-3-5.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EnterpriseLicensePicturesDetails implements Serializable{

  private String url;
}
