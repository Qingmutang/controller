package com.bjpowernode.p2p.admin.mapper;

import java.util.List;
import java.util.Map;

import com.bjpowernode.p2p.admin.model.UserInfo;

public interface UserInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    /**
     * 根据用户名和密码查询用户信息（登录）
     * 
     * @param paramMap
     * @return
     */
    UserInfo selectUserinfoByUserNameAndPassword(Map<String, Object> paramMap);

    /**
	 * 根据用户ID查询用户详细信息
	 * 
	 * @param userId
	 * @return
	 */
	UserInfo selectUserInfoDetail(Integer userId);

	/**
	 * 分页查询用户详细信息
	 * 
	 * @param paramMap
	 * @return
	 */
	List<UserInfo> selectUserInfoDetailByPage(Map<String, Object> paramMap);

	/**
	 * 查询符合分页条件的数据总条数
	 * 
	 * @param paramMap
	 * @return
	 */
	int selectUserInfoDetailByTotal(Map<String, Object> paramMap);
}