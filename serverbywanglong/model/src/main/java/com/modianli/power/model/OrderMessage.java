/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author hantsy
 */
public class OrderMessage implements Serializable {

    private Long userId;
    private Long productId;
    private String orderSN;
    private BigDecimal amount;

    public OrderMessage() {
    }

    public OrderMessage(Long userId, Long productId, String orderSN, BigDecimal amount) {
        this.userId = userId;
        this.productId = productId;
        this.orderSN = orderSN;
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    @Override
    public String toString() {
        return "OrderMessage{" + "userId=" + userId + ", productId=" + productId + ", orderSN=" + orderSN + ", amount=" + amount + '}';
    }

}
