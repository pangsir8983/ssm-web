package com.pangsir.helper.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于的使浏览器不缓存页面的过滤器
 *
 * @author 刘文铭
 * @see com.pangsir.helper.filter.ClearCacheFilter
 * @since 1.0
 */
@WebFilter(filterName = "ClearCacheFilter")
public class ClearCacheFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Cache-Control", "no-cache");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setDateHeader("Expires", -1);
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
