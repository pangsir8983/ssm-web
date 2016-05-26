package com.pangsir.helper.zip;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * @author 刘文铭
 * @日期 2012 2012-11-26 下午12:40:27
 * @描述 扩展ServletOutputStream功能，具有压缩的功能
 *
 */
public class GZipServletOutputStreamHelper extends ServletOutputStream {
	private GZIPOutputStream gzipOutputStream ;
	
	public GZipServletOutputStreamHelper(ServletOutputStream servletOutputStream)throws IOException {
		//使用GZIPOutputStream来增加压缩功能
		this.gzipOutputStream = new GZIPOutputStream(servletOutputStream);
    }

	@Override
	public void write(int b) throws IOException {
		//输出是通过GZIPOutputStream的write()来压缩输出
		gzipOutputStream.write(b);
	}

	public GZIPOutputStream getGzipOutputStream() {
    	return gzipOutputStream;
    }


	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {

	}
}
