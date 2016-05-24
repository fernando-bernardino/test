package com.voyanta.test.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInterceptor extends HandlerInterceptorAdapter {
	private static Log logger = LogFactory.getLog(LogInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
        long startTime = (Long) request.getAttribute("startTime");
        request.removeAttribute("startTime");
 
        long endTime = System.currentTimeMillis();
        
        logger.debug(request.getRequestURI() + " executed in " + (endTime - startTime) + "ms, status " + response.getStatus());
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exceptionIfAny) throws Exception {
	}
}