package com.bjpowernode.p2p.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjpowernode.p2p.admin.mapper.StaffInfoMapper;
import com.bjpowernode.p2p.admin.mapper.UserInfoMapper;
import com.bjpowernode.p2p.admin.model.StaffInfo;
import com.bjpowernode.p2p.admin.model.UserInfo;
import com.bjpowernode.p2p.admin.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private StaffInfoMapper staffInfoMapper;
	
	/**
	 * 根据用户名和密码查询用户（登录）
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	public UserInfo getUserinfoByUserNameAndPassword(Map<String, Object> paramMap) {
		return userInfoMapper.selectUserinfoByUserNameAndPassword(paramMap);
	}
	
	/**
	 * 根据用户ID查询用户详细信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserInfo getUserInfoDetail(Integer userId) {
		return userInfoMapper.selectUserInfoDetail(userId);
	}
	
	/**
	 * 更新用户最近登录时间
	 * 
	 * @param user
	 * @return
	 */
	public int updateUserInfo(UserInfo userInfo) {
		return userInfoMapper.updateByPrimaryKeySelective(userInfo);
	}
	
	/**
	 * 分页查询用户详细信息
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<UserInfo> getUserInfoDetailByPage(Map<String, Object> paramMap) {
		return userInfoMapper.selectUserInfoDetailByPage(paramMap);
	}

	/**
	 * 查询符合分页条件的数据总条数
	 * 
	 * @param paramMap
	 * @return
	 */
	public int getUserInfoDetailByTotal(Map<String, Object> paramMap) {
		return userInfoMapper.selectUserInfoDetailByTotal(paramMap);
	}
	
	/**
	 * 根据用户id删除用户
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteUser(Integer id) {
		return userInfoMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public UserInfo getUserInfoById(Integer id) {
		return userInfoMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public int addUser(UserInfo user) {
		return userInfoMapper.insertSelective(user);
	}
	
	/**
	 * 根据手机号的前几位搜索员工表中的手机号
	 * 
	 * @param startPhone
	 * @return
	 */
	public List<Map<String, Object>> getStaffPhone(String startPhone) {
		return staffInfoMapper.selectStaffPhone(startPhone);
	}
	
	/**
	 * 根据手机号查询员工信息
	 * 
	 * @param phone
	 * @return
	 */
	public StaffInfo getStaffInfoByPhone(String phone) {
		return staffInfoMapper.selectStaffInfoByPhone(phone);
	}
}
