package com.bjpowernode.p2p.interceptor;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bjpowernode.p2p.constants.Constants;
import com.bjpowernode.p2p.model.user.User;

/**
 * Spring MVC 拦截器，验证登录
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {

	private static final Logger logger = LogManager.getLogger(LoginInterceptor.class);
	
	/**
	 * 全局登录拦截器
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		Map<String,String[]> pramMap = request.getParameterMap();
		
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String[]> entry: pramMap.entrySet()) {
			sb.append(entry.getKey()).append("=");
		    sb.append(Arrays.toString(entry.getValue()));
		    sb.append("--");
		}
		logger.info("请求：" + request.getServletPath() + "-->" + sb);
		
		User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
		if (null == user) {//用户未登录，重定向到登录页面
			String path = request.getContextPath();
			response.sendRedirect(path + "/login.jsp");
		    return false;  
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}