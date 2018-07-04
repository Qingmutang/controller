package com.czb.themis.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * http属性配置信息
 * @author japper
 * @since 2018年6月19日
 */
@Data
@ConfigurationProperties(prefix="http")
public class HttpConfigProperties {
	  /**
	   * 长连接的存活时间(单位：second)
	   */
	  private int timeToLive;
	  /**
	   * 最大连接数
	   */
	  private int maxTotal;
	  /**
	   * 同路由的并发数
	   */
	  private int maxPerRoute;
	  /**
	   * 连接超时时间（毫秒）
	   */
	  private int connectTimeout;
	  /**
	   * 数据读取超时时间（毫秒）
	   */
	  private int readTimeout;
	  /**
	   * 连接不够用的等待时间，不宜过长（毫秒）
	   */
	  private int connectionRequestTimeout;
	  /**
	   * 是否应该在内部缓冲请求体,当通过POST或PUT发送大量数据时，建议将此属性更改为false，以免耗尽内存。
	   */
	  private boolean bufferRequestBody;
}
