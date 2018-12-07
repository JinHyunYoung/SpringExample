package com.spring.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private HttpSession session;
	@Override
	public boolean preHandle(HttpServletRequest request,
			       HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("123123123123123");
		if(session.getAttribute("userid")==null){
			response.sendRedirect(""
					+ "");
			return false;
		}
		return true;
	}
}
