/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 *
 * @author hansy
 */
public class UpdateMobileNumberForm implements Serializable {

    @NotNull
    @NotEmpty
    private String mobileNumber;

    @NotNull
    @NotEmpty
    private String smsCode;

    @NotNull
    @NotEmpty
    private String newMobileNumber;

    @NotNull
    @NotEmpty
    private String newSmsCode;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getNewMobileNumber() {
        return newMobileNumber;
    }

    public void setNewMobileNumber(String newMobileNumber) {
        this.newMobileNumber = newMobileNumber;
    }

    public String getNewSmsCode() {
        return newSmsCode;
    }

    public void setNewSmsCode(String newSmsCode) {
        this.newSmsCode = newSmsCode;
    }

    @Override
    public String toString() {
        return "UpdateMobileNumberForm{" + "mobileNumber=" + mobileNumber + ", smsCode=" + smsCode + ", newMobileNumber=" + newMobileNumber + ", newSmsCode=" + newSmsCode + '}';
    }

}
