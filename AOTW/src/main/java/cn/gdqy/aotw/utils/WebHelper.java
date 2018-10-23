package cn.gdqy.aotw.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.gdqy.aotw.pojo.User;

public class WebHelper {

	public static HttpSession getSession() {
		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		 return request.getSession();
	}
	
	public static User getCurrentUser() {
		return (User) getSession().getAttribute("user");
	}
}
