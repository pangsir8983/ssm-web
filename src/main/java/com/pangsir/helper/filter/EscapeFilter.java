package com.pangsir.helper.filter;

import com.pangsir.helper.filter.wrapper.EscapeWrapper;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;



/**
 * 对前台输入的HTML标签进行过滤
 * @author 刘文铭
 * @see com.pangsir.helper.filter.EscapeFilter
 * @since 1.0
 */
public class EscapeFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
	        FilterChain chain) throws IOException, ServletException {
		//将原来请求对象包裹至EscaperWrapper中
		HttpServletRequest requestWrapper = new EscapeWrapper((HttpServletRequest) req);
		//将EscapeWrapper对象当做请求对象传入doFilter中
		chain.doFilter(requestWrapper, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
