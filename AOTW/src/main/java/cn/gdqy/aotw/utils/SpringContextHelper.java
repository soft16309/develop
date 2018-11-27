package cn.gdqy.aotw.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * spring上下文帮助类
 * @author zrz
 */
public class SpringContextHelper {
	
	public static ApplicationContext getApplicationContext() {
		return ContextLoader.getCurrentWebApplicationContext();
	}
	
	public static<T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	
	public static Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}
}
