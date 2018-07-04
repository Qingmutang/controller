/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.common.captcha;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

import com.modianli.power.common.cache.CacheService;
import com.modianli.power.model.ImgCaptchaResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.UUID;

import javax.imageio.ImageIO;

/**
 *
 * @author hansy
 */
public class KaptchaDelegate {

    private static final Logger log = LoggerFactory.getLogger(KaptchaDelegate.class);

    private Properties props = new Properties();
    private Producer kaptchaProducer = null;
    private static final String CACHE_KEY_PREFIX = "kaptcha:";
    private static final int TIMEOUT_IN_SECONDS = 90;

    private CacheService cacheService;

    public KaptchaDelegate(CacheService cacheService) {
        this.cacheService = cacheService;

        ImageIO.setUseCache(false);
        this.props.put(Constants.KAPTCHA_BORDER, "yes");
        this.props.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        this.props.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");
        this.props.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        Config config = new Config(this.props);
        this.kaptchaProducer = config.getProducerImpl();
    }

    public ImgCaptchaResult generateCaptcha() {
        if (log.isDebugEnabled()) {
            log.debug("generating captcha...@ kaptchaProducer @" + this.kaptchaProducer);
        }
        String capText = this.kaptchaProducer.createText();
        if (log.isDebugEnabled()) {
            log.debug("generating captcha @" + capText);
        }
        String key = UUID.randomUUID().toString();
        if (log.isDebugEnabled()) {
            log.debug("storing in cache, key @" + key);
        }

        BufferedImage bi = this.kaptchaProducer.createImage(capText);

        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final String formatName = "jpg";
        try {
            ImageIO.write(bi, formatName, os);
            
            byte[] resultBytes= Base64.encode(os.toByteArray());
            
            this.cacheService.set(CACHE_KEY_PREFIX + key, capText, TIMEOUT_IN_SECONDS);
            
            return new ImgCaptchaResult(key, new String(resultBytes, Charset.forName("UTF-8")));
        } catch (final IOException ioe) {
            log.error("exception @" + ioe.getMessage());
        }
        return null;
    }

    public String getGeneratedCaptcha(String key) {
        String generatedValue = (String) this.cacheService.get(CACHE_KEY_PREFIX + key);
        if (log.isDebugEnabled()) {
            log.debug("get generated value in cache @" + generatedValue);
        }
        return generatedValue;
    }

}
