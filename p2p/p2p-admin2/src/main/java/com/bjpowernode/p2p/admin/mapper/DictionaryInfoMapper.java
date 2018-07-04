package com.bjpowernode.p2p.admin.mapper;

import java.util.List;

import com.bjpowernode.p2p.admin.model.DictionaryInfo;

public interface DictionaryInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(DictionaryInfo record);

    int insertSelective(DictionaryInfo record);

    DictionaryInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DictionaryInfo record);

    int updateByPrimaryKey(DictionaryInfo record);

	List<DictionaryInfo> getAllDictionaryInfo();

	List<DictionaryInfo> selectDictionaryInfoByType(int type);
}