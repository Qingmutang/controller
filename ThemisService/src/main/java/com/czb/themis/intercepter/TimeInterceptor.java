package com.czb.themis.intercepter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统请求日志拦截器
 *
 * @author ljmatlight
 * @date 2018/6/14
 */
@Slf4j
@Component
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("req-startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) {
        long startTime = (Long) request.getAttribute("req-startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
//        String ip = IpKit.getRealIp(request);

        StringBuilder stringBuilder = new StringBuilder(request.getRequestURI());
        stringBuilder.append(" 耗时: ");
        stringBuilder.append(executeTime);
        stringBuilder.append("毫秒 ");
        stringBuilder.append(JSON.toJSONString(request.getParameterMap()));
        stringBuilder.append(" ");
//        stringBuilder.append(ip);
        stringBuilder.append(" ");
        stringBuilder.append(request.getLocalPort());
        log.info(stringBuilder.toString());
    }

}