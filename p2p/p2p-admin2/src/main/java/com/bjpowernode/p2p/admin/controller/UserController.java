package com.bjpowernode.p2p.admin.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjpowernode.p2p.admin.constants.Constants;
import com.bjpowernode.p2p.admin.model.StaffInfo;
import com.bjpowernode.p2p.admin.model.UserInfo;
import com.bjpowernode.p2p.admin.rto.ResponseObject;
import com.bjpowernode.p2p.admin.service.UserInfoService;

@Controller
public class UserController {
	
	/**Log4j2的日志记录器*/
	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 账户登录
	 * 
	 * @param session
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping("/admin/login")
	public @ResponseBody ResponseObject login (HttpSession session, @RequestParam("userName") String userName,
			@RequestParam("password") String password) {
		
		ResponseObject responseObject = new ResponseObject();
		//先验证一下合法性
		if (StringUtils.isEmpty(userName)) {
			responseObject.setErrorCode(Constants.ERROR);
			responseObject.setErrorMessage("请输入登录账号");
			return responseObject;
		} else if (StringUtils.isEmpty(password)) {
			responseObject.setErrorCode(Constants.ERROR);
			responseObject.setErrorMessage("请输入登录密码");
			return responseObject;
		} else {
			//去数据库查询验证
			Map<String, Object> paramMap = new ConcurrentHashMap<String, Object>();
			paramMap.put("userName", userName);
			paramMap.put("password", password);
			
			//登录
			//UserInfo userInfo = userInfoService.getUserinfoByUserNameAndPassword(paramMap);
			
			UserInfo userInfo = new UserInfo(1,"name","password");
			
			//用户信息放入session
			if (null == userInfo) {//登录失败，没有查询到匹配的用户
				responseObject.setErrorCode(Constants.ERROR);
				responseObject.setErrorMessage("账户或密码不匹配");
				return responseObject;
			}
			
			//更新用户最近登录时间
			UserInfo user = new UserInfo();
			user.setId(userInfo.getId());
			user.setLastlogintime(new Date());
			
			int updateRow = userInfoService.updateUserInfo(user);
			logger.info("更新用户最近登录时间, userId=" + userInfo.getId() + ", 更新结果=" + updateRow);
			
			//用户信息放入session
			session.setAttribute(Constants.SESSION_USER_INFO, userInfo);
			
			//返回成功或失败的信息
			responseObject.setErrorCode(Constants.OK);
			responseObject.setErrorMessage("登录成功");
			return responseObject;
		}
	}
	
	/**
	 * 登录后跳转至该路径
	 * 
	 * @return
	 */
	@RequestMapping("/admin/profile")
	public String profile (HttpSession session, Model model) {
		
		UserInfo userInfo = (UserInfo)session.getAttribute(Constants.SESSION_USER_INFO);
		
		//查询用户的详细信息
		UserInfo userInfoDetail = userInfoService.getUserInfoDetail(userInfo.getId());
		
		//把查询出来的用户详细信息放入Model中，在前端页面展示
		model.addAttribute("userInfoDetail", userInfoDetail);
		
		return "profile";
	}
	
	/**
	 * 用户分页列表
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/users")
	public String users (Model model, @RequestParam(value="currentPage", required=false) Integer currentPage,
			@RequestParam(value="pageSize", required=false) Integer pageSize) {
		
		if (null == currentPage) {
			currentPage = 1;
		}
		if (null == pageSize) {
			pageSize = Constants.DEFAULT_PAGE_SIZE;
		}
		
		Map<String, Object> paramMap = new ConcurrentHashMap<String, Object>();
		
		//查询符合分页条件的数据总条数
		int totalRows = userInfoService.getUserInfoDetailByTotal(paramMap);
		
		//计算总页数
		int totalPage = totalRows / pageSize;
		int yushu = totalRows % pageSize;
		if (yushu > 0) {
			totalPage = totalPage + 1;
		}
		//判断前端传过来的当前页是否大于总页数
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		
		//数据库查询的开始行
		int startRow = (currentPage -1) * pageSize;
		paramMap.put("startRow", startRow);//数据库查询的开始行
		paramMap.put("pageSize", pageSize);//每页展示多少条数据
		
		//分页查询用户详细信息
		List<UserInfo> userInfoList = userInfoService.getUserInfoDetailByPage(paramMap);
		
		//把查询出来的分页用户详细信息放入到model中
		model.addAttribute("userInfoList", userInfoList);//当前页的数据
		model.addAttribute("startRow", startRow);//查询开始行
		model.addAttribute("totalPage", totalPage);//能够分多少页，也就是总页数
		model.addAttribute("currentPage", currentPage);//当前是第几页
		model.addAttribute("pageSize", pageSize);//每页显示的数据条数
		model.addAttribute("totalRows", totalRows);//查询符合分页条件的数据总条数
		
		//跳转到前台的users.jsp页面去展示
		return "users";
	}
	
	/**
	 * 登录后跳转至该路径
	 * 
	 * @return
	 */
	@RequestMapping("/admin/delete")
	public @ResponseBody int delete (Model model,
			@RequestParam(value="currentPage", required=false) Integer currentPage,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="userId", required=true) Integer id) {
		
		if (null == currentPage) {
			currentPage = 1;
		}
		if (null == pageSize) {
			pageSize = Constants.DEFAULT_PAGE_SIZE;
		}
		
		//根据用户id删除用户
		int deleteRow = userInfoService.deleteUser(id);
		
		//返回删除结果
		return deleteRow;
	}
	
	/**
	 * 去修改用户
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/toEditUser")
	public String toEditUser (Model model, @RequestParam(value="userId", required=true) Integer id) {
		
		//根据用户id查询用户信息
		UserInfo userInfo = userInfoService.getUserInfoDetail(id);
		
		model.addAttribute("user", userInfo);
		
		//返回页面
		return "addUser";
	}
	
	/**
	 * 去添加用户
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/toAddUser")
	public String toAddUser () {
		//返回页面
		return "addUser";
	}
	
	/**
	 * 自动提示的ajax请求员工手机号
	 * 
	 * @param startPhone
	 * @return
	 */
	@RequestMapping("/admin/getStaffPhone")
	public @ResponseBody Object getStaffPhone (@RequestParam("term") String startPhone) {
		
		List<Map<String, Object>> phoneList = userInfoService.getStaffPhone(startPhone);
		
		String[] arrayPhone = new String[phoneList.size()];
		for (int i=0; i<phoneList.size(); i++) {
			Map<String, Object> map = phoneList.get(i);
			arrayPhone[i] = String.valueOf(map.get("phone"));
		}
		//返回手机号数组
		return arrayPhone;
	}
	
	/**
	 * 添加或修改用户
	 * 
	 */
	@RequestMapping("/admin/addUser")
	public @ResponseBody ResponseObject addUser (Model model, 
			@RequestParam(value="username", required=false) String username,
			@RequestParam(value="password", required=false) String password,
			@RequestParam(value="phone", required=false) String phone,
			@RequestParam(value="userId", required=false) Integer id) {
		
		ResponseObject responseObject = new ResponseObject();
		
		if (StringUtils.isEmpty(username)) {
			responseObject.setErrorCode(Constants.ERROR);
			responseObject.setErrorMessage("用户账户为空");
			return responseObject;
		} else if (StringUtils.isEmpty(password)) {
			responseObject.setErrorCode(Constants.ERROR);
			responseObject.setErrorMessage("用户密码为空");
			return responseObject;
		} else if (StringUtils.isEmpty(phone)) {
			responseObject.setErrorCode(Constants.ERROR);
			responseObject.setErrorMessage("用户手机号为空");
			return responseObject;
		} else {
			//根据 phone 查询一下员工信息
			StaffInfo staffInfo = userInfoService.getStaffInfoByPhone(phone);
			if (null == staffInfo) {
				responseObject.setErrorCode(Constants.ERROR);
				responseObject.setErrorMessage("用户手机号不合法");
				return responseObject;
			}
			//封装用户对象
			UserInfo user = new UserInfo ();
			user.setUsername(username);
			user.setPassword(password);
			user.setStaffid(staffInfo.getId());
			if (null == id) {
				//添加用户
				int addRow = userInfoService.addUser(user);
				
				if (addRow > 0) {
					responseObject.setErrorCode(Constants.OK);
					responseObject.setErrorMessage("操作成功");
					return responseObject;
				} else {
					responseObject.setErrorCode(Constants.ERROR);
					responseObject.setErrorMessage("添加用户失败");
					return responseObject;
				}
			} else {
				//修改用户
				user.setId(id);
				int updateRow = userInfoService.updateUserInfo(user);
				
				if (updateRow > 0) {
					responseObject.setErrorCode(Constants.OK);
					responseObject.setErrorMessage("操作成功");
					return responseObject;
				} else {
					responseObject.setErrorCode(Constants.ERROR);
					responseObject.setErrorMessage("修改用户失败");
					return responseObject;
				}
			}
		}
	}
}
