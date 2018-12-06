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
		//System.out.println("preHandle Call");
		String reqUrl = request.getRequestURL().toString();
		System.out.println("reqUrl "+reqUrl);
		/*
		System.out.println("11111111111 : "+reqUrl);*/
		/*System.out.println(session.getAttribute("userid"));
		if(!reqUrl.equals("http://localhost:8080/") || session.getAttribute("userid")==null){
			System.out.println("aaa");
			return true;
		}
		
		else {
			System.out.println("bbbbb");
			response.sendRedirect("/");
			return true;
			
		}
		if(session.getAttribute("userid")==null){
			System.out.println("1222222222222222222222222222222222");
			response.sendRedirect("/");
			return false;
		}
		return true;
		
		*/
		//!reqUrl.equals("http://localhost:8080/")
		System.out.println(session.getAttribute("userid"));
		if(reqUrl.equals("http://localhost:8080/") || reqUrl.equals("http://localhost:8080/login") ) {
			return true;	
		} else {
			if(session.getAttribute("userid")==null){
				System.out.println("aaa");
				response.sendRedirect("/");
				return false;
			}
			return true;
		}
	}
	
}
