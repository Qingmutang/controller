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
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjpowernode.p2p.admin.constants.Constants;
import com.bjpowernode.p2p.admin.model.CreditorRights;
import com.bjpowernode.p2p.admin.model.DictionaryInfo;
import com.bjpowernode.p2p.admin.service.CreditorRightsService;
import com.bjpowernode.p2p.admin.service.DictionaryInfoService;
import com.bjpowernode.p2p.admin.util.MyDateUtils;

/**
 * 债权处理相关Controller
 * 
 * @author yanglijun
 *
 */
@Controller
public class CreditorRightsController {

	private static final Logger logger = LogManager.getLogger(CreditorRightsController.class);

	/**注入creditorRightsService*/
	@Autowired
	private CreditorRightsService creditorRightsService;
	
	@Autowired
	private DictionaryInfoService dictionaryInfoService;
	
	/**
	 * 查询债权信息列表
	 * 
	 * @param model
	 * @param productType
	 * @return
	 */
	@RequestMapping(value="/admin/zizhu")
	public String zizhu (Model model, 
			@RequestParam(value="currentPage", required=false) Integer currentPage,
			@RequestParam(value="pageSize", required=false) Integer pageSize
			) {
		
		Integer creditorRightsType = 0;
		return this.creditorRights(model, currentPage, creditorRightsType, pageSize);
	}
	
	/**
	 * 查询债权信息列表
	 * 
	 * @param model
	 * @param productType
	 * @return
	 */
	@RequestMapping(value="/admin/sanfang")
	public String sanfang (Model model, 
			@RequestParam(value="currentPage", required=false) Integer currentPage,
			@RequestParam(value="pageSize", required=false) Integer pageSize
			) {
		
		Integer creditorRightsType = 1;
		return this.creditorRights(model, currentPage, creditorRightsType, pageSize);
	}
	
	/**
	 * 查询债权信息列表
	 * 
	 * @param model
	 * @param productType
	 * @return
	 */
	public String creditorRights (Model model, Integer currentPage, Integer creditorRightsType, Integer pageSize) {

		if (null == currentPage) {
			currentPage = 1;//当前页从1开始
		}
		if (null == pageSize) {
			pageSize = Constants.DEFAULT_PAGE_SIZE;//默认每页展示10条数据
		}
		
		Map<String, Object> paramMap = new ConcurrentHashMap<String, Object>();
		if (null != creditorRightsType) {
			paramMap.put("creditorRightsType", creditorRightsType);
		}
		//符合查询条件的数据总条数
		int totalRows = creditorRightsService.getCreditorRightsByTotal(paramMap);
		
		//计算有多少页
		int totalPage = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPage = totalPage + 1;
		}
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		
		int startRow = (currentPage-1) * pageSize;
		paramMap.put("startRow", startRow);
		paramMap.put("pageSize", pageSize);
		
		//查询债权列表数据
		List<CreditorRights> creditorRightsList = creditorRightsService.getCreditorRightsByPage(paramMap);
		
		
		
		model.addAttribute("creditorRightsType", creditorRightsType);//产品类型
		model.addAttribute("creditorRightsList", creditorRightsList);//产品列表
		model.addAttribute("totalPage", totalPage);//总页数
		model.addAttribute("startRow", startRow);//开始行
		model.addAttribute("currentPage", currentPage);//当前页
		model.addAttribute("totalRows", totalRows);//总数据条数
		model.addAttribute("pageSize", pageSize);//每页显示数据条数
		
		return "creditorRights";
	}
	
	/**
	 * 进入债权发布页面
	 * 
	 * @param model
	 * @param creditorRightsType
	 * @return
	 */
	@RequestMapping(value="/admin/toAddCreditorRights")
	public String toAddCreditorRights (Model model) {
		
		Integer creditorRightsType = 0;
		
		//获取字典表的字典数据
		List<DictionaryInfo> dictionaryInfoList = dictionaryInfoService.getAllDictionaryInfo();
		model.addAttribute("dictionaryInfoList", dictionaryInfoList);
		
		//借款申请编号
		model.addAttribute("applyNo", "PN" + MyDateUtils.getCurrentDateByFormat());
		
		//债权类型
		model.addAttribute("creditorRightsType", creditorRightsType);
		return "addCreditorRights";
	}
	
	/**
	 * 添加债权
	 * 
	 * @param model
	 * @param creditorRightsType
	 * @return
	 */
	@RequestMapping(value="/admin/addCreditorRights")
	public String addCreditorRights (Model model, 
			CreditorRights creditorRights,
			@RequestParam(value="creditorRightsId") Integer creditorRightsId,
			@RequestParam(value="creditorRightsType") Integer creditorRightsType) {
		
		if (null != creditorRightsId) {
			//修改债权操作
			logger.info("修改债权信息,id=" + creditorRightsId);
			creditorRights.setId(creditorRightsId);
			creditorRightsService.updateCreditorRights(creditorRights);
			return "redirect:/admin/zizhu";
		} else {
			//添加操作
			creditorRightsService.addCreditorRights(creditorRights);
			return "redirect:/admin/zizhu";
		}
	}
	
	/**
	 * 去编辑债权信息
	 * 
	 * @param model
	 * @param creditorRights
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/admin/toEditCreditorRights")
	public String getCreditorRights (Model model,
			@RequestParam(value="creditorRightsType") Integer creditorRightsType,
			@RequestParam(value="creditorRightsId") Integer id) {
		
		//获取债权信息
		CreditorRights creditorRights = creditorRightsService.getCreditorRights(id);
		model.addAttribute("creditorRights", creditorRights);
		
		//获取字典表的字典数据
		List<DictionaryInfo> dictionaryInfoList = dictionaryInfoService.getAllDictionaryInfo();
		model.addAttribute("dictionaryInfoList", dictionaryInfoList);
		
		model.addAttribute("creditorRightsType", creditorRightsType);
		
		return "addCreditorRights";
	}
	
	/**
	 * 删除债权信息
	 * 
	 * @param model
	 * @param creditorRights
	 * @param creditorRightsType
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/admin/deleteCreditorRights")
	public @ResponseBody int deleteCreditorRights (@RequestParam(value="creditorRightsId") Integer id) {
		
		int deleteRow = creditorRightsService.deleteCreditorRights(id);
		return deleteRow;
	}
}
