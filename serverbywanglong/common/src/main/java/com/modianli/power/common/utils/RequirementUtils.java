package com.modianli.power.common.utils;

import com.google.common.collect.Maps;

import com.modianli.power.domain.jpa.Requirements;
import com.modianli.power.model.RequirementBiddingForm;
import com.modianli.power.model.RequirementForm;
import com.modianli.power.model.RequirementValidatedForm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by dell on 2017/5/26.
 *
 * Util for requirement
 */
public class RequirementUtils {

  /********************************************************
   *                public
   *******************************************************/
  /**
   * 校验需求信息
   */
  public static String isLegalForm(RequirementForm form, boolean checkPrice){
    String errMsg = null;
    if(form.getBiddingEndDate().isBefore(LocalDate.now())){
      errMsg = "截止日期应该在当前日期之后";
    }
    if(!money.containsKey(form.getMoney())){
      errMsg = "资金来源参数不存在";
    }

    if(checkPrice){
      if(form.getPrice().compareTo(new BigDecimal(0)) < 0){
        errMsg = "标书价格要不小于0元";
      }
    }
    return errMsg;
  }

  public static String isLegalForm(RequirementValidatedForm validatedForm){
    String errMsg = null;
    if(validatedForm.getBiddingEndDate().isBefore(LocalDate.now())){
      errMsg = "截止日期应该在当前日期之后";
    }
    if(!money.containsKey(validatedForm.getMoney())){
      errMsg = "资金来源参数不存在";
    }
    if(validatedForm.getPrice().compareTo(new BigDecimal(0)) < 0){
      errMsg = "标书价格要不小于0元";
    }
    return errMsg;
  }

  /**
   * 校验需求配单信息
   */
  public static String isLegalForm(RequirementBiddingForm form){
    String errMsg = null;
    if(form.getPrice().compareTo(new BigDecimal(0)) <= 0){
      errMsg = "标书报价必须大于0元";
    }

    return errMsg;
  }



  /********************************************************
   *                private
   *******************************************************/
  private static Map<String, String> categories = Maps.newHashMap();
  private static Map<String, String> money = Maps.newHashMap();
  public static Map<String, String> categoryNameMap = Maps.newHashMap();
  static {
    //load status to map
    for(Requirements.CategoryType categoryType : Requirements.CategoryType.values()){
      categories.putIfAbsent(categoryType.name(), categoryType.name());
	}
	//load money to map
    for(Requirements.TYPE type : Requirements.TYPE.values()){
      money.putIfAbsent(type.name(), type.name());
    }

    /**
     * CONSTRUCT,//施工
     DESIGN,//设计
     CONSTRUCT_DESIGN,//设计施工一体化
     SUPERVISE,//监理
     HR,//人力资源
     MATERIAL,//物资和设备采购
     CONSULT,//电力建设咨询
     OTHER//其他
     */
    categoryNameMap.putIfAbsent("CONSTRUCT", "施工");
    categoryNameMap.putIfAbsent("DESIGN", "设计");
    categoryNameMap.putIfAbsent("CONSTRUCT_DESIGN", "设计施工一体化");
    categoryNameMap.putIfAbsent("SUPERVISE", "监理");
    categoryNameMap.putIfAbsent("HR", "人力资源");
    categoryNameMap.putIfAbsent("MATERIAL", "物资和设备采购");
    categoryNameMap.putIfAbsent("CONSULT", "电力建设咨询");
    categoryNameMap.putIfAbsent("OTHER", "其他");
  }
}
