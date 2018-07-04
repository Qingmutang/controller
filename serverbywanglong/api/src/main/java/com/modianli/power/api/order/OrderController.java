package com.modianli.power.api.order;

import com.modianli.power.api.security.CurrentUser;
import com.modianli.power.common.exception.InvalidRequestException;
import com.modianli.power.common.service.OrderService;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.ApiErrors;
import com.modianli.power.model.OrderDetails;
import com.modianli.power.model.OrderForm;
import com.modianli.power.model.OrderSN;
import com.modianli.power.model.OrderSearchCriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;

@RestController
@RequestMapping(value = ApiConstants.URI_API + ApiConstants.URI_API_ORDER)
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Inject
    private OrderService orderService;

    @RequestMapping(value = {}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<OrderDetails>> getAllOrders(
        @RequestParam("type") String type,//
        @CurrentUser UserAccount user,//
        @PageableDefault(page = 0, size = 10, sort = {"placedDate"}, direction = Direction.DESC) Pageable page) {
        if (log.isDebugEnabled()) {
            log.debug("get all orders by criteria@" + type);
        }

        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setActive("true");
        criteria.setType(type);

        Page<OrderDetails> result = orderService.findOrders(criteria, user.getId(), page);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = {}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<OrderSN> createOrder(//
                                               @RequestBody OrderForm form, //
                                               @CurrentUser UserAccount user, //
                                               BindingResult errors,//
                                               UriComponentsBuilder uriComponentsBuilder) {

        if (log.isDebugEnabled()) {
            log.debug("placing order... @" + user + ", order @" + form);
        }

        if (errors.hasErrors()) {
            throw new InvalidRequestException(ApiErrors.INVALID_REQUEST, errors);
        }

        OrderSN sn = orderService.placeOrder(user, form);
        return new ResponseEntity<>(sn, HttpStatus.OK);
    }

    @RequestMapping(value = {"/{sn}"}, method = RequestMethod.GET, params = "by=SN")
    @ResponseBody
    public ResponseEntity<OrderDetails> getOrderBySerialNumber(@PathVariable("sn") String serialNumber) {

        if (log.isDebugEnabled()) {
            log.debug("get order data by serialNumber @" + serialNumber);
        }

        OrderDetails product = orderService.findOrderBySerialNumber(serialNumber);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = {"/{id}"}, params = "!by", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<OrderDetails> getOrder(@PathVariable("id") Long id) {

        if (log.isDebugEnabled()) {
            log.debug("get order data by id @" + id);
        }

        OrderDetails product = orderService.findOrderById(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = {"/{id}"}, params = "by=PREPARE_PAYMENT", method = RequestMethod.GET)
    public ResponseEntity<OrderDetails> preparePayment(@PathVariable("id") Long id) {

        if (log.isDebugEnabled()) {
            log.debug("request repayment by order id @" + id);
        }

        orderService.preparePaymentByOrderId(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public ResponseEntity<OrderDetails> cancelOrder(@PathVariable("id") Long id) {

        if (log.isDebugEnabled()) {
            log.debug("cancel order by id @" + id);
        }

        orderService.cancelOrderById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
