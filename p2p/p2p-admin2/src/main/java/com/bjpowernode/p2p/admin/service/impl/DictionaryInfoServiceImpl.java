package com.bjpowernode.p2p.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjpowernode.p2p.admin.mapper.DictionaryInfoMapper;
import com.bjpowernode.p2p.admin.model.DictionaryInfo;
import com.bjpowernode.p2p.admin.service.DictionaryInfoService;

@Service("dictionaryInfoService")
public class DictionaryInfoServiceImpl implements DictionaryInfoService {

	@Autowired
	private DictionaryInfoMapper dictionaryInfoMapper;
	
	/**
	 * 查询所有的字典表数据
	 * 
	 */
	@Override
	public List<DictionaryInfo> getAllDictionaryInfo() {
		return dictionaryInfoMapper.getAllDictionaryInfo();
	}
	
	/**
	 * 根据类型查询字典表数据
	 * 
	 * @param type
	 * @return
	 */
	@Override
	public List<DictionaryInfo> getDictionaryInfoByType(int type) {
		return dictionaryInfoMapper.selectDictionaryInfoByType(type);
	}
}