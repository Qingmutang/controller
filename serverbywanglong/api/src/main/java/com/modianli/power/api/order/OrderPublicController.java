package com.modianli.power.api.order;

import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.common.service.OrderService;
import com.modianli.power.common.utils.MsgUtils;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.OrderDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = ApiConstants.URI_API_PUBLIC + ApiConstants.URI_API_ORDER)
public class OrderPublicController {

    private static final Logger log = LoggerFactory.getLogger(OrderPublicController.class);

    @Inject
    private OrderService orderService;

    @RequestMapping(value = {"/{sn}"}, method = RequestMethod.GET, params = "by=SN")
    @ResponseBody
    public ResponseEntity<OrderDetails> getOrderBySerialNumber(@PathVariable("sn") String serialNumber) {

        if (log.isDebugEnabled()) {
            log.debug("get order details by serialNumber @" + serialNumber);
        }

        OrderDetails order = orderService.findOrderBySerialNumber(serialNumber);

        if (!order.isActive()) {
            throw new ResourceNotFoundException(serialNumber);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    
	@RequestMapping(value = {"/{productId}" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Page<OrderDetails>> getOrdersByProductId(
			@PathVariable("productId") Long productId,
			@PageableDefault(page = 0, size = 10, sort = "placedDate", direction = Sort.Direction.DESC) Pageable page) {

		if (log.isDebugEnabled()) {
			log.debug("get order details by productId @" + productId);
		}

		Page<OrderDetails> orders = orderService
				.findActiveOrdersByProductId(productId, page);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public ResponseEntity<?> verify(HttpServletRequest request){
	  try {
		return ResponseEntity.ok(orderService.verify(request.getParameterMap(), false));
	  } catch (Exception e) {
		return new ResponseEntity<Object>(MsgUtils.getMsg("验证失败"), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}

  @RequestMapping(value = "/sync/verify", method = RequestMethod.POST)
  public ResponseEntity<?> syncVerify(HttpServletRequest request){
	try {
	  return ResponseEntity.ok(orderService.syncVerify(request.getParameterMap()));
	} catch (Exception e) {
	  return ResponseEntity.ok("failed");
	}
  }
}
