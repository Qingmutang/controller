package com.modianli.power.model;

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
public class CasePubDetails {

  private String year;

  private List<CaseDetails> cases;

}
