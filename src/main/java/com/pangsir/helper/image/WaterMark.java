package com.pangsir.helper.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.swing.ImageIcon;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class WaterMark {
	private WaterMark(){}
	/**
	 * 给图片添加水印，另起画布，先画图片再画水印 
	 * 参数如下：
	 *  filePath 需要添加水印的图片的路径 
	 *  markContent 水印的文字
	 *  markContentColor 水印文字的颜色 
	 *  qualNum 图片质量
	 */
	public static void createMark(String filePath, String markContent,
			Color markContentColor, float qualNum) {
		ImageIcon imgIcon = new ImageIcon(filePath);
		Image theImg = imgIcon.getImage();
		// 图片大小
		int width = theImg.getWidth(null);
		int height = theImg.getHeight(null);

		BufferedImage bimage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		g.setColor(markContentColor);
		g.setBackground(Color.white);
		g.drawImage(theImg, 0, 0, null);
		// g.drawString(markContent, width-markContent.length()*6 , height-10 );
		// // 添加水印的文字和设置水印文字出现的内容
		g.drawString(markContent, 100, 100);
		// 释放绘图对象
		g.dispose();
		try {
			// 输出新图片
			FileOutputStream out = new FileOutputStream(filePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(qualNum, true);
			encoder.encode(bimage, param);
			out.close();

		} catch (Exception e) {
			throw new RuntimeException("水印加载失败");
		}
	}

	// 测试
	public static void main(String[] args) {

		WaterMark wm = new WaterMark();
		wm.createMark("d://map.jpg", "http://img.china.alibaba.com/",
				Color.red, 200f);

	}
}
