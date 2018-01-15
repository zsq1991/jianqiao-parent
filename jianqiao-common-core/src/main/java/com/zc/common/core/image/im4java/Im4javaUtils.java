package com.zc.common.core.image.im4java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;

/**
 * im4java工具类需要按照笔记中的image下面的大图片处理方案安装插件
 * 
 * @author zhangkaoqin
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-12-18 下午11:00:40
 * 
 */
public class Im4javaUtils {
	private Im4javaUtils() {
	}

	/**
	 * 获取文件大小
	 * 
	 * @param imgPath
	 * @return
	 */
	public static int getSize(String imgPath) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(imgPath);
			return inputStream.available();
		} catch (FileNotFoundException e) {
			return 0;
		} catch (IOException e) {
			return 0;
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * 获取图片的信息
	 * 
	 * @param imgPath
	 * @param pattemString %w代表宽度，%h代表高度 %d文件所在的文件夹路径 %f代表文件名称 %b代表文件大小单位为M
	 *            %[EXIF:DateTimeOriginal]代表文件的创建日期
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 */
	public static String getImageInfo(String imgPath, String pattemString) throws IOException,
			InterruptedException, IM4JavaException {
		IMOperation operation = new IMOperation();
		operation.format(pattemString);
		operation.addImage(1);
		IdentifyCmd identifyCmd = new IdentifyCmd(true);
		ArrayListOutputConsumer outputConsumer = new ArrayListOutputConsumer();
		identifyCmd.setOutputConsumer(outputConsumer);
		identifyCmd.run(operation, imgPath);
		ArrayList<String> cmdOutput = outputConsumer.getOutput();
		if (cmdOutput.size() == 1) {
			return cmdOutput.get(0);
		} else {
			return "error";
		}
	}

	/**
	 * 裁剪图片
	 * 
	 * @param imagePath 源图片路径
	 * @param newPath 处理后图片路径
	 * @param x 起始X坐标
	 * @param y 起始Y坐标
	 * @param width 裁剪宽度
	 * @param height 裁剪高度
	 * @return 返回true说明裁剪成功,否则失败
	 */
	public static boolean cutImage(String imagePath, String newPath, int x, int y, int width,
			int height) {
		boolean flag = false;
		try {
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			/** width：裁剪的宽度 * height：裁剪的高度 * x：裁剪的横坐标 * y：裁剪纵坐标 */
			op.crop(width, height, x, y);
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);
			convert.run(op);
			flag = true;
		} catch (IOException e) {
			System.out.println("文件读取错误!");
			flag = false;
		} catch (InterruptedException e) {
			flag = false;
		} catch (IM4JavaException e) {
			flag = false;
		} finally {

		}
		return flag;
	}

	/**
	 * 根据尺寸缩放图片[等比例缩放:参数height为null,按宽度缩放比例缩放;参数width为null,按高度缩放比例缩放]
	 * 
	 * @param imagePath 源图片路径
	 * @param newPath 处理后图片路径
	 * @param width 缩放后的图片宽度
	 * @param height 缩放后的图片高度
	 * @return 返回true说明缩放成功,否则失败
	 */
	public static boolean zoomImage(String imagePath, String newPath, Integer width, Integer height) {

		boolean flag = false;
		try {
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			if (width == null) {// 根据高度缩放图片
				op.resize(null, height);
			} else if (height == null) {// 根据宽度缩放图片
				op.resize(width);
			} else {
				op.resize(width, height);
			}
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);
			convert.run(op);
			flag = true;
		} catch (IOException e) {
			System.out.println("文件读取错误!");
			flag = false;
		} catch (InterruptedException e) {
			flag = false;
		} catch (IM4JavaException e) {
			flag = false;
		} finally {

		}
		return flag;
	}

	/**
	 * 图片旋转
	 * 
	 * @param imagePath 源图片路径
	 * @param newPath 处理后图片路径
	 * @param degree 旋转角度
	 */
	public static boolean rotate(String imagePath, String newPath, double degree) {
		boolean flag = false;
		try {
			// 1.将角度转换到0-360度之间
			degree = degree % 360;
			if (degree <= 0) {
				degree = 360 + degree;
			}
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			op.rotate(degree);
			op.addImage(newPath);
			ConvertCmd cmd = new ConvertCmd(true);
			cmd.run(op);
			flag = true;
		} catch (Exception e) {
			flag = false;
			System.out.println("图片旋转失败!");
		}
		return flag;
	}

}
