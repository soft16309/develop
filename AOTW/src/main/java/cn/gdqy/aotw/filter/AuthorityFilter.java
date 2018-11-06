package cn.gdqy.aotw.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gdqy.aotw.pojo.User;



public class AuthorityFilter implements Filter  {
	private List<String> excludeUrls;
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		User user = (User) request.getSession().getAttribute("user");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		if (!excludeUrls.contains(url) && !("/").equals(url)) {
			if (user == null) {
				response.sendRedirect(contextPath + "/login.jsp");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String urls = config.getInitParameter("excludeUrls");
		String[] tmp = urls.split(",");
		excludeUrls = Arrays.asList(tmp);
	}

	@Override
	public void destroy() {		
	}

}
