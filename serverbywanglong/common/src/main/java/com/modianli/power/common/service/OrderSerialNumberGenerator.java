/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.common.service;

import com.modianli.power.common.cache.CacheService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

/**
 *
 * @author hantsy
 */
@Service
public class OrderSerialNumberGenerator {

    private static final Logger log = LoggerFactory.getLogger(OrderSerialNumberGenerator.class);

    private static final String ORDER_SN_CACHE_PREFIX = "PO:SN:";

    @Inject
    private CacheService cacheService;

    public String nextSerialNumber() {

        if (log.isDebugEnabled()) {
            log.debug("get nextSerialNumber @");
        }

        String ldt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (log.isDebugEnabled()) {
            log.debug("local date time format info@" + ldt);
        }

        AtomicLong currentSerialNumber = (AtomicLong) cacheService.get(ORDER_SN_CACHE_PREFIX + ldt);

        if (currentSerialNumber == null) {
            currentSerialNumber = new AtomicLong(1);
        }

        long nextKeyLong = currentSerialNumber.getAndIncrement();

        cacheService.set(ORDER_SN_CACHE_PREFIX + ldt, currentSerialNumber);

        String nextKeyString = String.valueOf(nextKeyLong);

        if (log.isDebugEnabled()) {
            log.debug("next key string@" + nextKeyString);
        }

        String appendedString = "";
        for (int i = 0; i < 6 - nextKeyString.length(); i++) {
            appendedString += "0";
        }

        if (log.isDebugEnabled()) {
            log.debug("appended string@" + appendedString);
        }

        return ldt + appendedString + nextKeyString;
    }

}
