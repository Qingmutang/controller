package com.modianli.power.model;

import java.io.Serializable;
import java.util.List;
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
public class IndustryDataDetails implements Serializable{
  private String titleName;
  List<IndustryCategoryDetails> lists;

}
