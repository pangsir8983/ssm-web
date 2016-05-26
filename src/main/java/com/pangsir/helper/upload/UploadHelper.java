package com.pangsir.helper.upload;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pangsir.helper.file.FileHelper;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;


/**
 * 针对于二阶段的上传简单的封装
 * @author 刘文铭
 * @see com.pangsir.helper.string.StringHelper
 * @since 1.0
 */
public class UploadHelper {

	private UploadHelper(){};

	private static  Map<String,Object> map = new HashMap<String,Object>();

	private static  String path ;

	private static  String folder = "upload";

	private final static String TEMP = "temp";

	private static int sizeThreshold = 300000;

	private static int maxSize = 20 * 1024 * 1024;

	private static int fileMaxSize = 5*1024 * 1024;

	private static  String REAL_FILE_NAME = "realName";

	private static  String SAVE_FILE_NAME = "saveName";

	private static  String FILE_SIZE_NAME = "fileSize";




	public static Map<String, Object> uploadFile(HttpServletRequest request) throws IOException {
		map.clear();
		return uploadFile(request,folder);
	}

	/**
	 *
	 * @param request
	 * @param uploadPath 上传目录
	 * @return map
	 * @throws IOException
     */
	public static Map<String, Object> uploadFile(HttpServletRequest request,String uploadPath) throws IOException {
		map.clear();

		if(uploadPath==null){
			uploadPath = path;
		}

		// upload的真实路径
		path = request.getSession().getServletContext()
		        .getRealPath(File.separator + uploadPath);
		// 建立联系
		File folder = new File(path);
		if (!folder.isDirectory()) {//判断该文件是否存在,之后再判断是不是文件夹
			folder.mkdirs();
		}

		// 上传的临时目录
		String temppath = request.getSession().getServletContext()
		        .getRealPath(File.separator + TEMP);
		File tempfolder = new File(temppath);
		if (!tempfolder.isDirectory()) {
            tempfolder.mkdirs();
		}
		// 1.检测是否是文件上传的请求
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {

			// 2.创建DiskFileItemFactory 实例[获得磁盘文件条目工厂]
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 当文件大小超过300k时，就在磁盘上建立临时文件
			factory.setSizeThreshold(sizeThreshold);
			// 设计文件上传的临时目录
			factory.setRepository(tempfolder);

			// 3.创建ServletFileUpload类的实例，，使用DiskFileItemFactory
			// 对象做为参数[高水平的API文件上传处理 ]
			ServletFileUpload upload = new ServletFileUpload(factory);// 需要注意，还有带参构造方法
			// 文件大小不能超过20M
			upload.setSizeMax(maxSize);
			// 单个文件不能超过5M
			upload.setFileSizeMax(fileMaxSize);

			// 声明输入输出流
			InputStream is = null;
			// 输出流
			OutputStream os = null;

			try {
				// 4.解析请求对象中所有的表单项,返回一个列表
				FileItemIterator iter = upload.getItemIterator(request);
				// 5.遍历 迭代器
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					// 取得表单空间的name对应的值
					String name = item.getFieldName();
					is = item.openStream();
					// 判断表单是否普通表单
					if (item.isFormField()) {
						//存储普通表单的值
						map.put(name, Streams.asString(is,"UTF-8"));
					} else {
						// 将文件以流的方式上传到服务器
						// 构建一个输出流，目的是将用户上传的文件以输出流的方式写入到应程序上下文的upload文件夹中
						String realName = FileHelper.getRealFileName(item
				                .getName());
						String saveName = FileHelper.generateFileName(realName);
						
						os = new BufferedOutputStream(new FileOutputStream(
						        new File(path, saveName)));
						map.put(REAL_FILE_NAME, realName);
						map.put(SAVE_FILE_NAME, saveName);
						map.put(FILE_SIZE_NAME, FileHelper.getFormetFileSize(is.available()));
						

						// 将输入流里的内容写入到输出流中，目的是在程序上下文的upload文件夹中创建用户上传的文件
						// 流的输入输出 要借助于缓冲区，即使用字节数组,1024为缓冲区大小-->1024byte
						byte[] buf = new byte[1024];
						// length代表读取的字节数量
						int length = 0;
						while ((length = is.read(buf)) > 0) {
							os.write(buf, 0, length);
						}
					}

				}

			} catch (FileUploadException e) {
				e.printStackTrace();
				throw new IOException("没有找到上传的文件"+e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				throw new IOException("上传失败,原因为:"+e.getMessage());
			} finally {
				try {
					if (os != null){
						os.flush();
						os.close();
					}

					if (is != null){
						is.close();
					}


				} catch (IOException e) {
					e.printStackTrace();
					throw new IOException("上传失败了,原因"+e.getMessage());
				}
			}

		}
		
		return map;
	}



	public static Map<String, Object> uploadFileBatch(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		//存普通控件
		List<Map<String,Object>> filedList = new ArrayList<Map<String,Object>>();
		//存文件
		List<Map<String,Object>> fileList = new ArrayList<Map<String,Object>>();

		// upload的真实路径
		String path = request.getSession().getServletContext()
		        .getRealPath(File.separator + "upload");
		// 判断目录是否存在
		File folder = new File(path);
		if (!folder.isDirectory()) {
			folder.mkdirs();
		}

		// 上传的临时目录
		String temppath = request.getSession().getServletContext()
		        .getRealPath(File.separator + "temp");
		File tempfolder = new File(temppath);
		if (!tempfolder.isDirectory()) {
            tempfolder.mkdirs();
		}
		// 1.检测是否是文件上传的请求
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {

			// 2.创建DiskFileItemFactory 实例[获得磁盘文件条目工厂]
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 当文件大小超过300k时，就在磁盘上建立临时文件
			factory.setSizeThreshold(300000);
			// 设计文件上传的临时目录
			factory.setRepository(tempfolder);

			// 3.创建ServletFileUpload类的实例，，使用DiskFileItemFactory
			// 对象做为参数[高水平的API文件上传处理 ]
			ServletFileUpload upload = new ServletFileUpload(factory);// 需要注意，还有带参构造方法
			// 文件大小不能超过20M
			upload.setSizeMax(20 * 1024 * 1024);
			// 单个文件不能超过5M
			upload.setFileSizeMax(5*1024 * 1024);

			// 声明输入输出流
			InputStream is = null;
			// 输出流
			OutputStream os = null;

			try {
				// 4.解析请求对象中所有的表单项,返回一个列表
				FileItemIterator iter = upload.getItemIterator(request);
				// 5.遍历 迭代器
				while (iter.hasNext()) {
					
					FileItemStream item = iter.next();
					// 取得表单空间的name对应的值
					String name = item.getFieldName();
					is = item.openStream();
					// 判断表单是否普通表单
					if (item.isFormField()) {
						//存储普通表单的值
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put(name, Streams.asString(is,"UTF-8"));
						filedList.add(map);
					} else {
						
						HashMap<String, Object> map = new HashMap<String, Object>();
						
						// 将文件以流的方式上传到服务器
						// 构建一个输出流，目的是将用户上传的文件以输出流的方式写入到应程序上下文的upload文件夹中
						String realName = FileHelper.getRealFileName(item
				                .getName());
						String saveName = FileHelper.generateFileName(realName);
						
						os = new FileOutputStream(
						        new File(path, saveName));
						map.put("realName", realName);
						map.put("saveName", saveName);
						map.put("fileSize", FileHelper.getFormetFileSize(is.available()));
						
						fileList.add(map);
						// 将输入流里的内容写入到输出流中，目的是在程序上下文的upload文件夹中创建用户上传的文件
						// 流的输入输出 要借助于缓冲区，即使用字节数组,1024为缓冲区大小-->1024byte
						byte[] buf = new byte[1024];
						// length代表读取的字节数量
						int length = 0;
						while ((length = is.read(buf)) > 0) {
							os.write(buf, 0, length);
						}
					}
					
				}
				
				
				resultMap.put("filedList", filedList);
				resultMap.put("fileList", fileList);
				

			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();

					if (os != null)
						os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
		return resultMap;
	}
}
