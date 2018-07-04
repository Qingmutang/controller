package com.modianli.power.common.utils;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by gao on 17-3-23.
 */
public class ClassUtils {

  public static <T> String getIndexName(Class<T> tClass) {
	boolean flag = tClass.isAnnotationPresent(Document.class);
	if (flag) {
	  Document document = tClass.getAnnotation(Document.class);
	  return document.indexName();
	}
	return null;
  }

}
