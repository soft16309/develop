package cn.gdqy.aotw.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gdqy.aotw.utils.RequestResponseContext;

/**
 * 通过过滤器传入当前请求的request、response对象
 * @author zrz
 */
public class HttpServletResponseFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		RequestResponseContext.setRquest(request);
		RequestResponseContext.setResponse(response);
		
		chain.doFilter(request, response);
		
		RequestResponseContext.removeRequest();
		RequestResponseContext.removeResponse();
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
