package com.czb.themis.config;

import com.czb.themis.util.view.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ApiResult exceptionHandle(Exception e) {

        log.error(" 出现错误：{}", e);

        //TODO 待完善……
        return ApiResult.ERROR;
    }
}
