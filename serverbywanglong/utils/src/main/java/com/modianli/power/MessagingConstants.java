/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power;

public class MessagingConstants {

  public static final String QUEUE_SMS = "m.queue.sms";

  public static final String ROUTING_SMS = "m.routing.sms";

  public static final String EXCHANGE_SMS = "m.exchange.direct.sms";

  public static final String QUEUE_EMAIL = "m.queue.email";

  public static final String ROUTING_EMAIL = "m.routing.email";

  public static final String EXCHANGE_EMAIL = "m.exchange.direct.email";

  public static final String QUEUE_INDEX = "m.queue.index";

  public static final String ROUTING_INDEX = "m.routing.index";

  public static final String EXCHANGE_INDEX = "m.exchange.direct.index";

  public static final String QUEUE_INDEX_BATCH = "m.queue.index.batch";

  public static final String ROUTING_INDEX_BATCH = "m.routing.index.batch";

  public static final String EXCHANGE_DIRECT_PICTURE = "m.exchange.direct.picture";

  public static final String QUEUE_PICTURE_BATCH = "m.queue.picture.batch";

  public static final String ROUTING_PICTURE_BATCH = "m.routing.picture.batch";

  public static final String EXCHANGE_DIRECT_PAYMENT = "m.exchange.direct.payment";

  public static final String QUEUE_PAYMENT = "m.queue.payment";

  public static final String ROUTING_PAYMENT = "m.routing.payment";

  /**
   * 需求配单所需的MQ配置信息
   * start:
   */
  public static final String EXCHANGE_REQ = "m.exchange.direct.req";

  public static final String ROUTING_REQ = "m.routing.req";

  public static final String QUEUE_REQ = "m.queue.req";
  /**
   * end.
   */

  //删除资源的队列
  public static final String EXCHANGE_DIRECT_RESOURCE = "m.exchange.direct.resource";

  public static final String QUEUE_RESOURCE_BATCH = "m.queue.resource.batch";

  public static final String ROUTING_RESOURCE_BATCH = "m.routing.resource.batch";

  //死信队列
  public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";

  public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

  public static final String DEAD_LETTER_EXCHANGE = "dead.letter.exchange";

  public static final String DEAD_LETTER_ROUTING_KEY = "dead.letter.routing.key";

  public static final String DEAD_LETTER_QUEUE = "dead.letter.queue";

  public static final String EXCHANGE_ENTERPRISE = "m.exchange.direct.enterprise";

  public static final String QUEUE_ENTERPRISE = "m.queue.enterprise";

  public static final String ROUTING_ENTERPRISE = "m.routing.enterprise";

}
