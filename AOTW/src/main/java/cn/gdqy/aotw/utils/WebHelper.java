package cn.gdqy.aotw.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.gdqy.aotw.pojo.User;

public class WebHelper {
	
	public static HttpServletRequest getHttpServletRequest() {
//		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return RequestResponseContext.getRequest();
	}
	
	public static HttpServletResponse getHttpServletResponse() {
//		HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
		return RequestResponseContext.getResponse();
	}
	
	public static ServletContext getServletContext() {
		 return getHttpServletRequest().getSession().getServletContext();
	}
	
	public static HttpSession getSession() {
		 return getHttpServletRequest().getSession();
	}
	
	public static User getCurrentUser() {
		return (User) getSession().getAttribute("user");
	}
}
