package com.modianli.power.model;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-2-23.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryPropertyDetails implements Serializable {

  Page<ProductPubDetails> productDetails;

  List<EquipmentThirdCategoryDetails> thirdCategoryDetails;

  List<EquipmentPropertyEsDetails> propertyDetails;

  String secondCategoryName;


}
