package com.modianli.power.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by gao on 17-2-24.
 */
@Controller
public class IndexController {

  @GetMapping(value = "/")
  public String index(){
	return "redirect:swagger-ui.html";
  }

}
