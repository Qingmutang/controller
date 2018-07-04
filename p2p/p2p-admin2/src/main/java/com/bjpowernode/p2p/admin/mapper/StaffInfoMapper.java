package com.bjpowernode.p2p.admin.mapper;

import java.util.List;
import java.util.Map;

import com.bjpowernode.p2p.admin.model.StaffInfo;

public interface StaffInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(StaffInfo record);

    int insertSelective(StaffInfo record);

    StaffInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StaffInfo record);

    int updateByPrimaryKey(StaffInfo record);

    /**
	 * 根据手机号的前几位搜索员工表中的手机号
	 * 
	 * @param startPhone
	 * @return
	 */
	List<Map<String, Object>> selectStaffPhone(String startPhone);

	/**
	 * 根据手机号查询员工信息
	 * 
	 * @param phone
	 * @return
	 */
	StaffInfo selectStaffInfoByPhone(String phone);
}