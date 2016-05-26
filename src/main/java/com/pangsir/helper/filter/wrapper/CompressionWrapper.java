package com.pangsir.helper.filter.wrapper;

import com.pangsir.helper.zip.GZipServletOutputStreamHelper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;



/**
 * @author 刘文铭
 * @日期 2012 2012-11-26 下午12:48:05
 * @描述
 *
 */
public class CompressionWrapper extends HttpServletResponseWrapper {
	
	private GZipServletOutputStreamHelper gzServletOutputStream;
	private PrintWriter printWrite;

	public CompressionWrapper(HttpServletResponse response) {
	    super(response);
    }

	@Override
    public ServletOutputStream getOutputStream() throws IOException {
	    //已调用过 getWriter()，在调用getOutputStream()就抛出异常
		if(printWrite != null){
			try {
	            throw new IllegalStateException();
            } catch (IllegalStateException e) {
	            e.printStackTrace();
            }
		}
		
		if(gzServletOutputStream == null){
			// 创建有压缩功能的GZipServletOutputStream对象
			gzServletOutputStream = new GZipServletOutputStreamHelper(this.getResponse().getOutputStream());
		}
		
		return gzServletOutputStream;
    }

	@Override
    public PrintWriter getWriter() throws IOException {
	    // 已调用过 getOutputStream()，在调用getWriter()就抛出异常
		if(gzServletOutputStream !=null){
			try {
	            throw new IllegalStateException();
            } catch (IllegalStateException e) {
	            e.printStackTrace();
            }
		}
		
		if(printWrite == null){
			gzServletOutputStream = new GZipServletOutputStreamHelper(this.getResponse().getOutputStream());
			OutputStreamWriter osw = new OutputStreamWriter(gzServletOutputStream,this.getResponse().getCharacterEncoding());
			printWrite = new PrintWriter(osw);
		}
		
	    return printWrite;
    }
	/**
	 * 不实现此方法内容，因为真正的输出会被压缩
	 */
	@Override
    public void setContentLength(int len) { }

	public GZIPOutputStream getGZIPOutputStream() {
		if(this.gzServletOutputStream == null){
			return null;
		}
		
    	return this.gzServletOutputStream.getGzipOutputStream();
    }



}
