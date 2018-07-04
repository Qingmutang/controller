/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

import java.io.Serializable;


public class SmsCaptchaResult implements Serializable {

    private boolean result;

    public SmsCaptchaResult(boolean result) {
        this.result = result;
    }

    public SmsCaptchaResult() {
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SmsCaptchaResult{" + "result=" + result + '}';
    }

}
