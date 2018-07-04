package com.bjpowernode.p2p.admin.service;

import java.util.List;
import java.util.Map;

import com.bjpowernode.p2p.admin.model.StaffInfo;
import com.bjpowernode.p2p.admin.model.UserInfo;

/**
 * 用户处理的Service接口
 * 
 * @author 
 *
 */
public interface UserInfoService {

	/**
	 * 根据用户名和密码查询用户（登录）
	 * 
	 * @param paramMap
	 * @return
	 */
	public UserInfo getUserinfoByUserNameAndPassword(Map<String, Object> paramMap);

	/**
	 * 根据用户ID查询用户详细信息
	 * 
	 * @param id
	 * @return
	 */
	public UserInfo getUserInfoDetail(Integer id);

	/**
	 * 更新用户最近登录时间
	 * 
	 * @param user
	 * @return
	 */
	public int updateUserInfo(UserInfo userInfo);

	/**
	 * 分页查询用户详细信息
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<UserInfo> getUserInfoDetailByPage(Map<String, Object> paramMap);

	/**
	 * 查询符合分页条件的数据总条数
	 * 
	 * @param paramMap
	 * @return
	 */
	public int getUserInfoDetailByTotal(Map<String, Object> paramMap);

	/**
	 * 根据用户id删除用户
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteUser(Integer id);

	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public UserInfo getUserInfoById(Integer id);

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public int addUser(UserInfo user);

	/**
	 * 根据手机号的前几位搜索员工表中的手机号
	 * 
	 * @param startPhone
	 * @return
	 */
	public List<Map<String, Object>> getStaffPhone(String startPhone);

	/**
	 * 根据手机号查询员工信息
	 * 
	 * @param phone
	 * @return
	 */
	public StaffInfo getStaffInfoByPhone(String phone);

}
