package com.modianli.power.api.order;

import com.modianli.power.common.service.OrderService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.BooleanValue;
import com.modianli.power.model.OrderDetails;
import com.modianli.power.model.OrderSearchCriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.URI_API_ORDER)
public class OrderMgtController {

    private static final Logger log = LoggerFactory.getLogger(OrderMgtController.class);

    @Inject
    private OrderService orderService;

    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Page<OrderDetails>> searchOrders(
        @RequestBody OrderSearchCriteria criteria,//
        @PageableDefault(page = 0, size = 10, sort = {"placedDate"}, direction = Direction.DESC) Pageable page) {
        if (log.isDebugEnabled()) {
            log.debug("get all orders by  search@" + criteria);
        }

        Page<OrderDetails> result = orderService.findOrders(criteria, null, page);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @RequestMapping(value = {"/{id}"}, params = "!action", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseEntity<Void> updateOrder(@PathVariable("id") Long id, @RequestBody OrderForm form) {
//
//        if (log.isDebugEnabled()) {
//            log.debug("orderForm @" + form);
//        }
//
//        orderService.updateOrder(id, form);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
    @RequestMapping(value = {"/sn/{sn}"}, method = RequestMethod.GET, params = "action=CHECK_EXISTENCE")
    @ResponseBody
    public ResponseEntity<BooleanValue> checkOrderExistenceBySerialNumber(@PathVariable("sn") String serialNumber) {

        if (log.isDebugEnabled()) {
            log.debug("check order existence by serial number @" + serialNumber);
        }

        OrderDetails product = orderService.findOrderBySerialNumber(serialNumber);

        return new ResponseEntity<>(new BooleanValue(true), HttpStatus.OK);
    }

    @RequestMapping(value = {"/{sn}"}, method = RequestMethod.GET, params = "by=SN")
    @ResponseBody
    public ResponseEntity<OrderDetails> getOrderBySerialNumber(@PathVariable("sn") String serialNumber) {

        if (log.isDebugEnabled()) {
            log.debug("get order details by serialNumber @" + serialNumber);
        }

        OrderDetails product = orderService.findOrderBySerialNumber(serialNumber);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET, params = "!by")
    @ResponseBody
    public ResponseEntity<OrderDetails> getOrder(@PathVariable("id") Long id) {

        if (log.isDebugEnabled()) {
            log.debug("get order details by id @" + id);
        }

        OrderDetails order = orderService.findOrderById(id);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(value = {"/{id}"}, params = "action=DEACTIVATE", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> deactivateOrder(@PathVariable("id") Long id) {

        if (log.isDebugEnabled()) {
            log.debug("soft delete order @" + id + ", deactivate it ,  not really delete it");
        }

        orderService.deactivateOrder(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = {"/{id}"}, params = "action=ACTIVATE", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> activateOrder(@PathVariable("id") Long id) {

        if (log.isDebugEnabled()) {
            log.debug("recovery order @" + id + " from trashbox");
        }

        orderService.activateOrder(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
