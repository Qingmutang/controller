package com.bjpowernode.p2p.admin.mapper;

import java.util.List;
import java.util.Map;

import com.bjpowernode.p2p.admin.model.SlideShow;

/**
 * 轮播图相关处理Mapper
 * 
 * @author yanglijun
 *
 */
public interface SlideShowMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(SlideShow record);

    int insertSelective(SlideShow slideShow);

    SlideShow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SlideShow record);

    int updateByPrimaryKey(SlideShow record);
    
    List<SlideShow> getSlideShowByPage(Map<String, Object> paramMap);
    
    int getSlideShowByTotal(Map<String, Object> paramMap);
}