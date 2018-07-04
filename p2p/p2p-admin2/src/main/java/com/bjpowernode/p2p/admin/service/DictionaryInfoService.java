package com.bjpowernode.p2p.admin.service;

import java.util.List;

import com.bjpowernode.p2p.admin.model.DictionaryInfo;

public interface DictionaryInfoService {

	/**
	 * 查询所有的字典表数据
	 * 
	 * @return
	 */
	public List<DictionaryInfo> getAllDictionaryInfo ();
	
	/**
	 * 根据类型查询字典表数据
	 * 
	 * @param type
	 * @return
	 */
	public List<DictionaryInfo> getDictionaryInfoByType (int type);
}
