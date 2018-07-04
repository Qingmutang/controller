package com.bjpowernode.p2p.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bjpowernode.p2p.admin.constants.Constants;
import com.bjpowernode.p2p.admin.model.CreditorRights;
import com.bjpowernode.p2p.admin.service.CreditorRightsService;

/**
 * 合同管理
 * 
 * @author yanglijun
 *
 */
@Controller
public class ContractController {
	
	private static final Logger logger = LogManager.getLogger(ContractController.class);

	/**注入creditorRightsService*/
	@Autowired
	private CreditorRightsService creditorRightsService;
	
	@RequestMapping("/admin/contract")
	public String contract (Model model, 
			@RequestParam(value="currentPage", required=false) Integer currentPage,
			@RequestParam(value="pageSize", required=false) Integer pageSize) {
		
		if (null == currentPage) {
			currentPage = 1;//当前页从1开始
		}
		if (null == pageSize) {
			pageSize = Constants.DEFAULT_PAGE_SIZE;//默认每页展示10条数据
		}
		
		int startRow = (currentPage-1)*pageSize;
		Map<String, Object> paramMap = new ConcurrentHashMap<String, Object>();
		paramMap.put("startRow", startRow);
		paramMap.put("pageSize", pageSize);
		paramMap.put("matchStatus", 1);//债权匹配状态，0：等待匹配，1：匹配完成
		paramMap.put("collectStatus", 1);//募资状态，0：等待募集，1：募集完成
		
		//查询债权列表数据
		List<CreditorRights> creditorRightsList = creditorRightsService.getCreditorRightsByPage(paramMap);
		
		//符合查询条件的数据总条数
		int totalRows = creditorRightsService.getCreditorRightsByTotal(paramMap);
		
		//计算有多少页
		int totalPage = totalRows / Constants.DEFAULT_PAGE_SIZE;
		int mod = totalRows % Constants.DEFAULT_PAGE_SIZE;
		if (mod > 0) {
			totalPage = totalPage + 1;
		}
		
		model.addAttribute("creditorRightsList", creditorRightsList);//产品列表
		model.addAttribute("totalPage", totalPage);//总页数
		model.addAttribute("currentPage", currentPage);//当前页
		model.addAttribute("totalRows", totalRows);//总数据条数
		model.addAttribute("pageSize", pageSize);//每页显示数据条数
		
		return "contract";
	}
	
	/**
	 * 重新生成借款合同
	 * 
	 * @return
	 */
	@RequestMapping("/admin/generateContract")
	public String generateContract (
			@RequestParam(value="currentPage", required=false) Integer currentPage,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="creditorRightsId") Integer creditorRightsId) {
		
		logger.info("去重新生成债权合同-->" + creditorRightsId);
		
		//生成合同
		creditorRightsService.sealPdf(creditorRightsId);
		
		return "redirect:/admin/contract?currentPage="+currentPage+"&pageSize="+pageSize+"&creditorRightsId="+creditorRightsId;
	}
}
