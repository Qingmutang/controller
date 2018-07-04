package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndustryCategoryDetails implements Serializable {

 private static final long serialVersionUID = 6059705679241522312L;
 private Long id;
 private String name;

}
