package com.modianli.power.model;

import java.io.Serializable;

public class NewPasswordForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mobileNumber;

    private String password;

    private String captchaValue;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptchaValue() {
        return captchaValue;
    }

    public void setCaptchaValue(String captchaValue) {
        this.captchaValue = captchaValue;
    }
    
    @Override
    public String toString() {
        return "NewPasswordForm [mobileNumber=" + mobileNumber + ", password=" + password + ", captachValue=" + captchaValue + "]";
    }

}
