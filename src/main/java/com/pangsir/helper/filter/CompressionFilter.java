package com.pangsir.helper.filter;

import com.pangsir.helper.filter.wrapper.CompressionWrapper;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @author 刘文铭
 * @日期 2012 2012-11-26 下午1:06:56
 * @描述 
 *
 */
public class CompressionFilter implements Filter {
	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
	        FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		String encodings = req.getHeader("accept-encoding");
		//检查是否接收gzip压缩
		if(encodings != null&&encodings.indexOf("gzip")>-1){
			//创建响应封装器
			CompressionWrapper responseWrapper = new CompressionWrapper(resp);
			//设置响应内容编码为gzip格式
			responseWrapper.setHeader("content-encoding", "gzip");
			chain.doFilter(req, responseWrapper);
			GZIPOutputStream gzipOutputStream = responseWrapper.getGZIPOutputStream();
			if(gzipOutputStream!=null){
				//调用GZIPOutputStream的finish()方法完成压缩输出
				gzipOutputStream.finish();
			}
		}else{
			//不接受压缩，直接进行下一个过滤器
			chain.doFilter(req, resp);
		}
	}



}
