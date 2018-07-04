/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

import java.io.Serializable;


public class SmsCodeResult implements Serializable {

    private String mobile;
    private String result;
    private long createdDate;

    public SmsCodeResult(String mobile, String result, long createdDate) {
        this.mobile = mobile;
        this.result = result;
        this.createdDate = createdDate;
    }

    public SmsCodeResult() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "SmsCodeResult{" + "result=" + result + ", createdDate=" + createdDate + '}';
    }

}
