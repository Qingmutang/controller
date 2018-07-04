/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

import java.io.Serializable;

/**
 *
 * @author hansy
 */
public class OrderSN implements Serializable {

    private Long id;
    private String serialNumber;
    private String userCustId;
    private String borrowerCustId;

    public OrderSN() {
    }

    public OrderSN(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getUserCustId() {
        return userCustId;
    }

    public void setUserCustId(String userCustId) {
        this.userCustId = userCustId;
    }

    public String getBorrowerCustId() {
        return borrowerCustId;
    }

    public void setBorrowerCustId(String borrowerCustId) {
        this.borrowerCustId = borrowerCustId;
    }

    @Override
    public String toString() {
        return "OrderSN{" + "id=" + id + ", serialNumber=" + serialNumber + ", userCustId=" + userCustId + ", borrowerCustId=" + borrowerCustId + '}';
    }
}
