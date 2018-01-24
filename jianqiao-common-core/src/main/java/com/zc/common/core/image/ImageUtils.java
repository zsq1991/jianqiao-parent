package com.zc.common.core.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * 图片工具类 处理图片的缩放截取
 * 
 * @author zhangkaoqing
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2011-12-26 上午：11:04
 * 
 */
public class ImageUtils {

	/**
	 * 实现图像的等比缩放和缩放后的截取
	 * 
	 * @param inFilePath 要截取文件的路径
	 * @param outFilePath 截取后输出的路径
	 * @param width 要截取宽度
	 * @param hight 要截取的高度
	 * @param sign 是否也要删除inFilePath原图
	 * @throws Exception
	 */
	public static String saveImage(String inFilePath, String outFilePath, int width, boolean isDel)
			throws Exception {
		File file = new File(inFilePath);
		InputStream in = new FileInputStream(file);
		File saveFile = new File(outFilePath);
		String newName = outFilePath;

		BufferedImage srcImage = ImageIO.read(in);
		if (width > 0) {
			// 原图的宽度
			int sw = srcImage.getWidth();
			// 原图的高度
			int sh = srcImage.getHeight();
			// 如果原图像的大小小于要缩放的图像大小，直接将要缩放的图像复制过去
			if (sw > width) {
				double percent = (double) width / sw;
				int hight = (int) (percent * sh);
				srcImage = resize(srcImage, width, hight);
				// 缩放后的图像的宽和高
				int w = srcImage.getWidth();
				int h = srcImage.getHeight();
				// 如果缩放后的图像和要求的图像宽度一样，就对缩放的图像的高度进行截取
				if (w == width) {
					// 计算X轴坐标
					int x = 0;
					int y = h / 2 - hight / 2;
					saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
				} else if (h == hight) {
					// 否则如果是缩放后的图像的高度和要求的图像高度一样，就对缩放后的图像的宽度进行截取
					// 计算X轴坐标
					int x = w / 2 - width / 2;
					int y = 0;
					saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
				}
				in.close();
			} else {
				String fileName = saveFile.getName();
				String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
				ImageIO.write(srcImage, formatName, saveFile);
				return newName;
			}
		}
		if (isDel) {
			// 调用delete()方法
			boolean result = file.delete();
			if (result) {
				System.out.println("文件" + inFilePath + "删除成功");
			} else {
				System.out.println("文件" + inFilePath + "删除失败");
			}
		}
		return newName;
	}

	/**
	 * 实现图像的等比缩放
	 * 
	 * @param source 源文件
	 * @param targetW 目标宽度
	 * @param targetH 目标高度
	 * @return
	 */
	private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	/**
	 * 实现缩放后的截图
	 * 
	 * @param image 缩放后的图像
	 * @param subImageBounds 要截取的子图的范围
	 * @param subImageFile 要保存的文件
	 * @throws IOException
	 */
	private static void saveSubImage(BufferedImage image, Rectangle subImageBounds,
			File subImageFile) throws IOException {
		if (subImageBounds.x < 0 || subImageBounds.y < 0
				|| subImageBounds.width - subImageBounds.x > image.getWidth()
				|| subImageBounds.height - subImageBounds.y > image.getHeight()) {
			System.out.println("Bad   subimage   bounds");
			return;
		}
		BufferedImage subImage = image.getSubimage(subImageBounds.x, subImageBounds.y,
				subImageBounds.width, subImageBounds.height);
		String fileName = subImageFile.getName();
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
		ImageIO.write(subImage, formatName, subImageFile);
	}

	/***
	 * 功能：压缩图片变成小尺寸自动选择小的比例
	 * 
	 * 参数1：oImage：原图；*
	 * 
	 * 参数2：maxWidth：小尺寸宽度；*
	 * 
	 * 参数3：maxHeight：小尺寸长度；*
	 * 
	 * 参数4：newImageName：小尺寸图片存放的路径和新名字
	 * 
	 * 参数5：fileType：小尺寸图片类型（png,gif,jpg...）
	 * 
	 * @throws IOException
	 ***/

	public static void compressImage(File srcImage, int maxWidth, int maxHeight,
			String newImageName, String fileTypes) throws IOException {
		BufferedImage image = ImageIO.read(srcImage);
		int srcWidth = image.getWidth();
		int srcHeight = image.getHeight();
		if (srcHeight <= maxHeight && srcWidth <= maxWidth) {
			ImageIO.write(image, fileTypes, new File(newImageName));
			return;
		}
		Image scaleimage = image.getScaledInstance(srcWidth, srcHeight, Image.SCALE_SMOOTH);
		double ratio = Math.min((double) maxWidth / srcWidth, (double) maxHeight / srcHeight);
		AffineTransformOp op = new AffineTransformOp(
				AffineTransform.getScaleInstance(ratio, ratio), null);
		scaleimage = op.filter(image, null);
		ImageIO.write((RenderedImage) scaleimage, fileTypes, new File(newImageName));
	}

	/***
	 * 功能：压缩图片变成小尺大小不自动改变
	 * 
	 * 参数1：oImage：原图；*
	 * 
	 * 参数2：maxWidth：小尺寸宽度；*
	 * 
	 * 参数3：maxHeight：小尺寸长度；*
	 * 
	 * 参数4：newImageName：小尺寸图片存放的路径和新名字
	 * 
	 * 参数5：fileType：小尺寸图片类型（png,gif,jpg...）
	 * 
	 * @throws IOException
	 ***/
	public static void compressAbsoluteImage(File srcImage, int maxWidth, int maxHeight,
			String newImageName, String fileTypes) throws IOException {
		BufferedImage image = ImageIO.read(srcImage);
		int srcWidth = image.getWidth();
		int srcHeight = image.getHeight();
		if (srcHeight <= maxHeight && srcWidth <= maxWidth) {
			ImageIO.write(image, fileTypes, new File(newImageName));
			return;
		}
		Image scaleimage = image.getScaledInstance(srcWidth, srcHeight, Image.SCALE_SMOOTH);
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(
				(double) maxWidth / srcWidth, (double) maxHeight / srcHeight), null);
		scaleimage = op.filter(image, null);
		ImageIO.write((RenderedImage) scaleimage, fileTypes, new File(newImageName));
	}
}
