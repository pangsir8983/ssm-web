package com.pangsir.helper.filter.wrapper;

import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * 基于Java Web阶段的处理获取的字符串为HTML的情况,我们需要进行数据转换
 * @author 刘文铭
 * @see com.pangsir.helper.file.FileHelper
 * @since 1.0
 */
public class EscapeWrapper extends HttpServletRequestWrapper {
	/*
	 * @see 必须调用父类构造器[构造方法],记住传入HttpServletRequest实例
	 */
	public EscapeWrapper(HttpServletRequest request) {
		super(request);
    }

	/*
	 * 重写getParameter()方法
	 */
	@Override
    public String getParameter(String name) {
		String value = this.getRequest().getParameter(name);
	    return StringEscapeUtils.escapeHtml(value);
    }
	

}
