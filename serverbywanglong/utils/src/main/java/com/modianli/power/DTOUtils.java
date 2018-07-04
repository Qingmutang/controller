package com.modianli.power;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gao on 17-1-17.
 */
public class DTOUtils {

  private static final ModelMapper INSTANCE = new ModelMapper();
  private static final ModelMapper STRICT_INSTANCE = init();
  private static final ModelMapper STRICT_NOTNULL_INSTANCE = initNotNull();

  private static ModelMapper init() {
	ModelMapper modelMapper = new ModelMapper();
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//	modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
	return modelMapper;
  }

  private static ModelMapper initNotNull() {
	ModelMapper modelMapper = new ModelMapper();
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
	return modelMapper;
  }

  public static <S, T> T map(S source, Class<T> targetClass) {
	return INSTANCE.map(source, targetClass);
  }

  public static <S, T> void mapTo(S source, T dist) {
	INSTANCE.map(source, dist);
  }

  public static <S, T> T strictMap(S source, Class<T> targetClass) {
	return STRICT_INSTANCE.map(source, targetClass);
  }

  public static <S, T> void strictMapTo(S source, T dist) {
	STRICT_INSTANCE.map(source, dist);
  }

  public static <S, T> T strictNotNullMap(S source, Class<T> targetClass) {
	return STRICT_NOTNULL_INSTANCE.map(source, targetClass);
  }

  public static <S, T> void strictNotNullMapTo(S source, T dist) {
	STRICT_NOTNULL_INSTANCE.map(source, dist);
  }

  public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
	List<T> list = new ArrayList<>();
	for (int i = 0; i < source.size(); i++) {
	  T target = INSTANCE.map(source.get(i), targetClass);
	  list.add(target);
	}
	return list;
  }

  public static <S, T> Page<T> mapPage(Page<S> source, Class<T> targetClass) {
	List<S> sourceList = source.getContent();
	List<T> list = new ArrayList<>();
	for (int i = 0; i < sourceList.size(); i++) {
	  T target = INSTANCE.map(sourceList.get(i), targetClass);
	  list.add(target);
	}
	return new PageImpl<>(list, new PageRequest(source.getNumber(), source.getSize(), source.getSort()),
						  source.getTotalElements());
  }

//  public static void main(String[] args) {
//
//  }
}