/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.common.captcha;

import com.modianli.power.common.cache.CacheService;
import com.modianli.power.model.ImgCaptchaRequest;
import com.modianli.power.model.ImgCaptchaResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author hansy
 */
@Service
public class CaptchaService {

    private static final Logger log = LoggerFactory.getLogger(CaptchaService.class);

    @Inject
    private CacheService cacheService;

    private KaptchaDelegate kaptchaImg;

    public CaptchaService() {
    }

    @PostConstruct
    public void onPostConstruct() {
        if (log.isDebugEnabled()) {
            log.debug("call  @PostConstruct in CaptchaService... ");
        }
        this.kaptchaImg = new KaptchaDelegate(cacheService);
    }

    public ImgCaptchaResult generateCaptcha() {
        return kaptchaImg.generateCaptcha();
    }

    public boolean verifyImgCaptcha(ImgCaptchaRequest res) {
        Assert.notNull(res, "captcha request can not be null");
        return res.getResponseValue().equalsIgnoreCase(kaptchaImg.getGeneratedCaptcha(res.getKey()));
    }

}
