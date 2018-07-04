/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.common.service;

import com.google.common.collect.Maps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modianli.power.DTOUtils;
import com.modianli.power.common.exception.OrderCanNotBeCancelledException;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.domain.jpa.OrderItem;
import com.modianli.power.domain.jpa.PurchaseOrder;
import com.modianli.power.domain.jpa.RequirementBidding;
import com.modianli.power.domain.jpa.TransactionLog;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.OrderDetails;
import com.modianli.power.model.OrderForm;
import com.modianli.power.model.OrderSN;
import com.modianli.power.model.OrderSearchCriteria;
import com.modianli.power.payment.alipay.util.AlipayCore;
import com.modianli.power.payment.alipay.util.AlipayNotify;
import com.modianli.power.persistence.repository.jpa.OrderItemRepository;
import com.modianli.power.persistence.repository.jpa.OrderRepository;
import com.modianli.power.persistence.repository.jpa.OrderSpecifications;
import com.modianli.power.persistence.repository.jpa.ProductRepository;
import com.modianli.power.persistence.repository.jpa.RequirementBiddingRepository;
import com.modianli.power.persistence.repository.jpa.TransactionLogRepository;
import com.modianli.power.persistence.repository.jpa.UserRepository;

import org.modelmapper.internal.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Service
@Transactional
public class OrderService {

  private static final Logger log = LoggerFactory.getLogger(OrderService.class);

  @Inject
  private OrderRepository orderRepository;

  @Inject
  private OrderItemRepository orderItemRepository;

  @Inject
  private TransactionLogRepository transactionLogRepository;

  @Inject
  private RequirementBiddingRepository biddingRepository;

  @Inject
  private ProductRepository productRepository;

  @Inject
  private RabbitTemplate rabbitTemplate;

  @Inject
  private OrderSerialNumberGenerator generator;

  @Inject
  private UserRepository userRepository;

  @Inject
  private OrderService orderService;

  @Inject
  private ObjectMapper objectMapper;

  public OrderSN placeOrder(UserAccount userAccount, OrderForm orderForm) {
	Assert.notNull(userAccount, "userId is not null");
	Assert.notNull(orderForm, "orderForm is not null");
	Assert.notNull(orderForm.getProductId(), "orderForm is not null");
	log.debug("place order user @{}, orderForm@{}", userAccount, orderForm);

	String serialNumber = generator.nextSerialNumber();
	log.debug("generating order serial number @{}", serialNumber);
	//订单
	PurchaseOrder purchaseOrder = DTOUtils.strictMap(orderForm, PurchaseOrder.class);
	purchaseOrder.setSerialNumber(serialNumber);//订单号
	purchaseOrder.setUser(userAccount);
	purchaseOrder = orderRepository.save(purchaseOrder);

	//流水号
	TransactionLog transactionLog = new TransactionLog();
	transactionLog.setSerialNumber(serialNumber);
	transactionLog.setOrder(purchaseOrder);
	transactionLog.setFee(purchaseOrder.getAmount());
	transactionLogRepository.save(transactionLog);

	//订单物品列表
	OrderItem orderItem = new OrderItem(purchaseOrder, orderForm.getProductId());
	orderItem.setProductId(orderForm.getProductId());
	orderItemRepository.save(orderItem);

	OrderSN sn = new OrderSN();
	sn.setSerialNumber(serialNumber);
	return sn;
  }

  public Page<OrderDetails> findOrders(OrderSearchCriteria criteria, Long userId, Pageable page) {
	if (log.isDebugEnabled()) {
	  log.debug("findOrders by @start @" + criteria + ", page@" + page);
	}

	Page<PurchaseOrder> orders = orderRepository.findAll(
		OrderSpecifications.searchOrders(
			criteria.getStart(),
			criteria.getEnd(),
			StringUtils.hasText(criteria.getStatus()) ? PurchaseOrder.Status.valueOf(criteria.getStatus()) : null,
			StringUtils.hasText(criteria.getActive()) ? Boolean.valueOf(criteria.getActive()) : true,
			userId),
		page);

	if (log.isDebugEnabled()) {
	  log.debug("all orders @" + orders.getTotalElements());
	}

	return DTOUtils.mapPage(orders, OrderDetails.class);
  }

  public void updateOrder(Long id, OrderForm form) {
	throw new UnsupportedOperationException(
		"Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  public OrderDetails findOrderBySerialNumber(String serialNumber) {
	Assert.notNull(serialNumber, "order serialNumber can not be null");

	if (log.isDebugEnabled()) {
	  log.debug(" findOrderBySerialNumber @" + serialNumber);
	}

	PurchaseOrder order = orderRepository.findBySerialNumber(serialNumber);

	if (order == null) {
	  throw new ResourceNotFoundException(serialNumber);
	}

	return DTOUtils.map(order, OrderDetails.class);
  }

  public OrderDetails findOrderById(Long id) {
	Assert.notNull(id, "order id can not be null");

	if (log.isDebugEnabled()) {
	  log.debug(" findOrderById @" + id);
	}

	PurchaseOrder order = orderRepository.findOne(id);

	if (order == null) {
	  throw new ResourceNotFoundException(id);
	}

	OrderDetails orderDetails = DTOUtils.map(order, OrderDetails.class);
	return orderDetails;

  }

  public void deactivateOrder(Long id) {
	Assert.notNull(id, "order id can not be null");
	orderRepository.updateActiveStatus(id, false);
  }

  public void activateOrder(Long id) {
	Assert.notNull(id, "order id can not be null");
	orderRepository.updateActiveStatus(id, true);
  }

  public Page<OrderDetails> findOrdersByUserId(Long userId, Pageable page) {
	Assert.notNull(userId, "userId can not be null");
	Page<PurchaseOrder> orders = orderRepository.findByUserId(userId, page);
	return DTOUtils.mapPage(orders, OrderDetails.class);
  }

  public void cancelOrderById(Long id) {
	Assert.notNull(id, "order id can not be null");
	PurchaseOrder order = orderRepository.findOne(id);

	if (order == null) {
	  throw new ResourceNotFoundException(id);
	}

	if (order.getStatus() != PurchaseOrder.Status.PENDING_PAYMENT
		&& order.getStatus() != PurchaseOrder.Status.PAYMENT_FAILED) {
	  throw new OrderCanNotBeCancelledException(id);
	}

	orderRepository.updateStatus(id, PurchaseOrder.Status.CANCELED);
  }

  public Page<OrderDetails> findActiveOrdersByProductId(Long productId,
														Pageable page) {
	Assert.notNull(productId, "Product id can not be null");

	if (log.isDebugEnabled()) {
	  log.debug("find active Orders by productId@" + productId);
	}
//	Page<PurchaseOrder> orders = orderRepository.findByActiveIsTrueAndProductId(productId, page);

	return DTOUtils.mapPage(null, OrderDetails.class);
  }

  public void preparePaymentByOrderId(Long id) {
	if (log.isDebugEnabled()) {
	  log.debug("prepare payment by order id @" + id);
	}

	PurchaseOrder ord = orderRepository.findOne(id);

	if (ord == null) {
	  throw new ResourceNotFoundException("order is not found by id");
	}
  }

  public Map<String, String> verify(Map<String, String[]> requestParams, boolean sync) throws Exception{
	Map<String,String> result = Maps.newHashMap();

    Map<String,String> params = Maps.newHashMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
	  String name = (String) iter.next();
	  String[] values = (String[]) requestParams.get(name);
	  String valueStr = "";
	  for (int i = 0; i < values.length; i++) {
		valueStr = (i == values.length - 1) ? valueStr + values[i]
											: valueStr + values[i] + ",";
	  }
	  params.put(name, valueStr);
	}

	//计算得出通知验证结果
	boolean verify_result = AlipayNotify.verify(params);
	if(verify_result){
	  params = AlipayCore.paraFilter(params);
	  //商户订单号
	  String out_trade_no = params.get("out_trade_no");
	  //支付宝交易号
	  String trade_no = params.get("trade_no");
	  //交易状态
	  String trade_status = params.get("trade_status");

	  PurchaseOrder purchaseOrder = orderRepository.findBySerialNumber(out_trade_no);
	  if(!PurchaseOrder.Status.PAID.equals(purchaseOrder.getStatus())){
	    //流水号
		TransactionLog transactionLog = transactionLogRepository.findBySerialNumber(out_trade_no);
		transactionLog.setTradeNo(trade_no);
		transactionLog.setResultJson(objectMapper.writeValueAsString(params));

		purchaseOrder.setPaidDate(LocalDateTime.now());
		if("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)){
		  purchaseOrder.setStatus(PurchaseOrder.Status.PAID);
		  transactionLog.setStatus(TransactionLog.Status.SUCCESS);
		  transactionLog.setTransactedDate(LocalDate.now());
		  //根据订单类型处理
		  //1.需求配单
		  if(PurchaseOrder.Type.REQUIREMENT.equals(purchaseOrder.getOrderType())){
			//更新requirement bidding 的状态
			List<OrderItem> orderItems = orderItemRepository.findByPurchaseOrder(purchaseOrder.getId());
			OrderItem orderItem = orderItems.get(0);
			biddingRepository.updateRequirementBiddingByPaystatus(RequirementBidding.PayStatus.PAID, orderItem.getProductId());
		  }else{
			//TODO ...
		  }
		}else{
		  purchaseOrder.setStatus(PurchaseOrder.Status.PAYMENT_FAILED);
		  transactionLog.setStatus(TransactionLog.Status.FAILED);
		}
		orderRepository.save(purchaseOrder);
		transactionLogRepository.save(transactionLog);
	  }else{
	    //存在情况：订单是支付状态,而配单状态是未支付状态
		if(PurchaseOrder.Type.REQUIREMENT.equals(purchaseOrder.getOrderType())){
		  //更新requirement bidding 的状态
		  List<OrderItem> orderItems = orderItemRepository.findByPurchaseOrder(purchaseOrder.getId());
		  OrderItem orderItem = orderItems.get(0);
		  biddingRepository.updateRequirementBiddingByPaystatus(RequirementBidding.PayStatus.PAID, orderItem.getProductId());
		}else{
		  //TODO ...
		}

	    transactionLogRepository.updateJsonBySN(trade_no, objectMapper.writeValueAsString(params));
	  }

	  result.put("msg", "验证成功");
	  result.put("result", "success");
	  result.putAll(params);
	}else{
	  result.put("msg", "验证失败");
	  result.put("result", "failed");
	}

	return result;
  }

  public String syncVerify(Map<String, String[]> parameterMap) throws Exception {
	Map<String, String> result = orderService.verify(parameterMap, true);
	log.warn(">>>> 支付宝异步通知， result: @{}", result);
	return result.get("result");
  }
}
