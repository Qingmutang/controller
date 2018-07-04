package com.modianli.power.common.utils;

import java.util.UUID;

/**
 * Created by gao on 17-6-28.
 */
public class UUIDUtils {

  public static String getShortUUID() {
	return UUID.randomUUID().toString().replaceAll("-", "");
  }

  public static String getLongUUID() {
	return UUID.randomUUID().toString();
  }
}
