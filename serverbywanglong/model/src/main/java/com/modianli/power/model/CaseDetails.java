package com.modianli.power.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/5/15.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaseDetails {

  private String projectName;

  private String projectDescription;

  private LocalDate projectTime;

  private List<String> urls;

  private Long id;

  private Boolean active;

}
