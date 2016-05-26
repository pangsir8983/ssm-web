package com.pangsir.helper.download;

import java.io.*;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 关于下载操作的封装,不允许进行实例化
 *
 * @author 刘文铭
 * @see com.pangsir.helper.download.DownloadHelper
 * @since 1.0
 */
public final class DownloadHelper {
    private DownloadHelper() {
    }

    /**
     * 设置默认的下载路径
     */
    private final static String PATH = "/upload";


    /**
     * 默认的下载方式
     */
    public static void download(HttpServletRequest request, HttpServletResponse response,String fileName) throws IOException {
        download(request,response,fileName,null,null);
    }

    /**
     * 设置自定义的下载目录
     */
    public static void download(HttpServletRequest request, HttpServletResponse response,String fileName,String downloadPath) throws IOException {
        download(request,response,fileName,downloadPath,null);
    }

    /**
     * 下载的时候显示自定定义的下载名称
     * 解决中文乱码问题
     */
    public static void download(String fileName,String realName,HttpServletRequest request, HttpServletResponse response) throws IOException {
        download(request,response,fileName,null,realName);
    }

    /**
     * 下载方法封装
     * @param request 请求信息
     * @param response 响应信息
     * @param fileName 下载文件的名
     * @param filePath 下载的路径
     * @param real_name 下载显示的名称
     * @throws IOException
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, String fileName, String filePath, String real_name) throws IOException {
        //1.判断路径是否存在
        if (filePath == null) {
            filePath = PATH;
        }
        File file = new File(filePath + File.separator + fileName);// 建立联系
        if (file.exists()) {//判断该文件是否存在

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));//涉及到效率问题

            byte[] buffer = new byte[1];//声明字节数组

            response.reset();

            response.setCharacterEncoding("UTF-8");//设置响应编码
            response.setContentType("multipart/form-data");//设置文件ContentType类型，这样设置，会自动判断下载文件类型

            /*判断下载的真实名称是否存在*/
            if(real_name==null){
                real_name = fileName;
            }else{
                real_name = URLEncoder.encode(real_name, "UTF-8").replace("+", "%20");//对文件进行编码和转换
            }
            /*下载设置-我没有记住*/
            response.setHeader("Content-disposition", "attachment;filename=" + real_name);


            OutputStream os = response.getOutputStream();
            while (bis.read(buffer) > 0) {
                os.write(buffer);
            }
            //清除资源
            bis.close();
            os.flush();
            os.close();
        } else {
            throw new FileNotFoundException("您下载的文件不存在,抱歉下载失败");
        }
    }



   /* public static void download(HttpServletRequest request, HttpServletResponse response, String fileName, String filePath, String real_name) throws IOException {
        //1.判断路径是否存在
        if(filePath==null){
            filePath = PATH;
        }
        File file = new File(filePath + File.separator + fileName);// 建立联系
        if (file.exists()) {//判断该文件是否存在

           //String agent = request.getHeader("User-Agent");//获取请求的头部信息

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));//涉及到效率问题

            byte[] buffer = new byte[1];//声明字节数组

            *//**
     * 来自网络:
     * getResponse的getWriter()方法连续两次输出流到页面的时候，第二次的流会包括第一次的流，所以可以使用response.reset或者resetBuffer的方法。
     * <code>reset(): </code>
     Clears any data that exists in the buffer as well as the status code and headers. If the response has been committed, this method throws an IllegalStateException.
     * <code>resetBuffer(): </code>
     Clears the content of the underlying buffer in the response without clearing headers or status code. If the response has been committed, this method throws an IllegalStateException.
     *
     * <b>可以看到resetBuffer方法与reset方法的区别是，头和状态码没有清除。</b>
     *
     *
     *//*
            response.reset();

            response.setCharacterEncoding("UTF-8");//设置响应编码
            response.setContentType("multipart/form-data");//设置文件ContentType类型，这样设置，会自动判断下载文件类型


            real_name = URLEncoder.encode(real_name, "UTF-8").replace("+", "%20");//对文件进行编码和转换
            *//**下载设置*//*
            response.setHeader("Content-disposition", "attachment;filename=" + real_name);

            *//*if (agent.indexOf("MSIE 7") > 0) {
                //解决下载名称显示中文名称的问题
                //real_name = real_name.length() > 100 ? URLEncoder.encode(real_name, "UTF-8").replace("+", "%20").substring(0, 100) + "." + real_name.substring(real_name.lastIndexOf(".") + 1) : URLEncoder.encode(real_name, "UTF-8").replace("+", "%20");
                response.setHeader("Content-disposition", "attachment;filename=" + real_name);
            } else if (agent.indexOf("Firefox") > 0) {
                response.setHeader("Content-disposition", "attachment;filename*=" + URLEncoder.encode(real_name, "utf-8").replace("+", "%20"));
            } else {
                response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(real_name, "utf-8").replace("+", "%20"));
            }*//*
            OutputStream os = response.getOutputStream();
            while (bis.read(buffer) > 0) {
                os.write(buffer);
            }
            bis.close();
            os.close();
        }else{

        }
    }*/


}
