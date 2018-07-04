package com.bjpowernode.p2p.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bjpowernode.p2p.admin.model.CreditorContract;
import com.bjpowernode.p2p.admin.model.CreditorRights;

public interface CreditorRightsMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(CreditorRights record);

    int insertSelective(CreditorRights record);

    CreditorRights selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CreditorRights record);

    int updateByPrimaryKey(CreditorRights record);

	List<CreditorRights> getCreditorRightsByPage(Map<String, Object> paramMap);

	int getCreditorRightsByTotal(Map<String, Object> paramMap);
	
	/**
	 * 查询符合生成合同条件的债权信息数据
	 * 
	 * @param creditorId
	 * @return
	 */
	List<CreditorContract> selectCreditorRightsForContract(@Param("creditorId") Integer creditorId);
}