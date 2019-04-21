package org.springframework.samples.petclinic;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class SessionInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		System.out.println(request.getRequestURI());
		System.out.println(request.getContextPath());
		
		if (request.getRequestURI().equals(request.getContextPath()+"/login")) {
			return true;
		}
		
		Object obj = request.getSession().getAttribute("loginInfo");
		if (obj == null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
		
	}

}
