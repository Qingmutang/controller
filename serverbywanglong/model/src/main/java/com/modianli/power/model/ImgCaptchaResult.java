/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author hansy
 */
public class ImgCaptchaResult implements Serializable {

    private String key;
    private String base64Data;
    private long timestamp;

    public ImgCaptchaResult() {
    }

    public ImgCaptchaResult(String key, String data) {
        this.key = key;
        this.base64Data = data;
        this.timestamp = new Date().getTime();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
