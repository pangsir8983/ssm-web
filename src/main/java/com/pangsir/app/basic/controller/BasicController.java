package com.pangsir.app.basic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *     控制器说明: 所有Controller的基础
 * </p>
 *
 * @Author: 胖先生
 * @Create: 2016-05-12 12:10
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */
public abstract class BasicController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;

    @Autowired
    protected ServletContext application;

    /**
     * 重定向至地址 url
     *
     * @param url
     *            请求地址
     * @return
     */
    protected String redirectTo( String url ) {
        StringBuffer rto = new StringBuffer("redirect:");
        rto.append(url);
        return rto.toString();
    }
}