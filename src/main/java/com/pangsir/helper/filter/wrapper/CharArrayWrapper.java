package com.pangsir.helper.filter.wrapper;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 一个<b>响应包装器</b>,需要所有客户端通常会输出并将它保存在一个大的字符数组。
 * @author 刘文铭
 * @see com.pangsir.helper.file.FileHelper
 * @since 1.0
 */
public class CharArrayWrapper extends HttpServletResponseWrapper {
	private CharArrayWriter charWriter;

	/*
	 * 初始化封装器 首先,这个构造函数调用父构造函数。
	 * 那叫是至关重要的,以便响应存储,因此setHeader,setStatus,addCookie等等正常工作。
	 * 第二,这个构造函数创建一个CharArrayWriter,将用于积累响应。
	 */
	public CharArrayWrapper(HttpServletResponse response) {
		super(response);
		charWriter = new CharArrayWriter();
	}

	/*
	 * 当servlet或JSP页面请求时,不要给他们真正的一个值。 相反,给他们一个版本,写进字符数组。
	 * 过滤器需要发送数组内容到客户端(也许在修改它)。
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		// return super.getWriter();
		return (new PrintWriter(charWriter));
	}

	/*
	 * 得到的一个字符串表示整个缓冲区。 一定不要多次调用该方法在相同的包装器。
	 *  API的CharArrayWriter并不能保证它“记住”的前一个值,
	 * 因此调用可能会使一个新的字符串每一次。
	 */
	@Override
	public String toString() {
		// return super.toString();
		return (charWriter.toString());
	}

	/*
	 * 获得底层字符数组
	 */
	public char[] toCharArray() {
		return (charWriter.toCharArray());
	}

}
