package com.pangsir.helper.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author 刘文铭
 * @描述 禁止站点过滤器(类似 资源保护过滤器或者是做权限路径过滤)
 * 配置说明 web.xml  
 *  <filter>  
 *    <filter-name>BannedAccessFilter</filter-name>  
 *    <filter-class>com.shxt.utils.filter.BannedAccessFilter</filter-class>  
 *    <init-param>  
 *       <param-name>bannedSites</param-name>  
 *       <param-value>  
 *           [url]www.competingsite.com[/url]  
 *           [url]www.bettersite.com[/url]  
 *           [url]www.moreservlets.com[/url]  
 *       </param-value>  
 *    </init-param>  
 *  </filter>  
 *  <filter-mapping>
 *  	<filter-name>BannedAccessFilter</filter-name> 
 *  	<url-pattern>/*</url-pattern> 
 *  </filter-mapping> 
 *       
 */
public class BannedAccessFilter implements Filter {
	private HashSet<String> bannedSiteTable;

	/**
	 * 拒绝访问如果请求来自一个禁止的网站或被称为一个禁止的网站在这里。
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
	        FilterChain chain) throws IOException, ServletException {
		/*System.out
		        .println("Within BannedAccessFilter:Filtering the Request...Start");*/
		HttpServletRequest req = (HttpServletRequest)request;
		String requestingHost = req.getRemoteHost();
		String referringHost = getReferringHost(req.getHeader("Referer"));
		String bannedSite = null;
		boolean isBanned = false;
		if (bannedSiteTable.contains(requestingHost)) {
			bannedSite = requestingHost;
			isBanned = true;
		} else if (bannedSiteTable.contains(referringHost)) {
			bannedSite = referringHost;
			isBanned = true;
		}
		if (isBanned) {
			showWarning(response, bannedSite);
		} else {
			chain.doFilter(request, response);
		}
		/*System.out
		        .println("Within BannedAccessFilter:Filtering the Response...");*/
	}

	/**
	 * 创建一个表被禁的网站的基于初始化参数 HashSet(判断给定键的存在)而不是比起散列表(它有一个值为每个键)。
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		bannedSiteTable = new HashSet<String>();
		String bannedSites = config.getInitParameter("bannedSites");
		// 默认值是空
		StringTokenizer tok = new StringTokenizer(bannedSites);
		while (tok.hasMoreTokens()) {
			String bannedSite = tok.nextToken();
			bannedSiteTable.add(bannedSite);
			System.out.println("禁止[Banned] " + bannedSite);
		}

	}

	@Override
	public void destroy() {

	}

	private String getReferringHost(String refererringURLString) {
		try {
			// Malformed or null
			URL referringURL = new URL(refererringURLString);
			return referringURL.getHost();
		} catch (MalformedURLException mue) {
			return null;
		}
	}

	private void showWarning(ServletResponse response, String bannedSite)
	        throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 "
		        + "Transitional//EN\">\n";
		out.println(docType
		        + "<HTML>\n<HEAD><TITLE>Access Prohibited</TITLE></HEAD>\n<BODY BGCOLOR=\"WHITE\">\n<H1>Access Prohibited</H1>\nSorry, access from or via\n is not allowed.\n</BODY></HTML>");
	}

}
