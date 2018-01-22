/**
 * Project Name:alqframework
 * File Name:ZxingUtils.java
 * Package Name:com.org.alqframework.zxing
 * Date:2016年3月14日下午5:01:38
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.zc.common.core.zxing;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Map;

/**
 * ClassName:ZxingUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年3月14日 下午5:01:38 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class ZxingUtils {
	  private static final int BLACK = 0xFF000000;
	  private static final int WHITE = 0xFFFFFFFF;

	  public static BufferedImage toBufferedImage(BitMatrix matrix) {
	    int width = matrix.getWidth();
	    int height = matrix.getHeight();
	    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for (int x = 0; x < width; x++) {
	      for (int y = 0; y < height; y++) {
	        image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
	      }
	    }
	    return image;
	  }

	  
	  public static void writeToFile(BitMatrix matrix, String format, File file)
	      throws IOException {
	    BufferedImage image = toBufferedImage(matrix);
	    if (!ImageIO.write(image, format, file)) {
	      throw new IOException("Could not write an image of format " + format + " to " + file);
	    }
	  }

	  
	  public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
	      throws IOException {
	    BufferedImage image = toBufferedImage(matrix);
	    if (!ImageIO.write(image, format, stream)) {
	      throw new IOException("Could not write an image of format " + format);
	    }
	  }
	  /**
	   * 生成二维码
	   * @param path 保存二维码的路径
	   * @param fileName 二维码图片的名称
	   * @param format 图片格式 例如jpg、png
	   * @param content 二维码保存的内容
	   * @param width 二维码图片的宽
	   * @param height 二维码图片的高
	   * @return
	   */
	  @SuppressWarnings({"unchecked","rawtypes", "deprecation"})
	  public static boolean createQrCode(String path,String fileName,String format,String content,int width,int height){
			try {
			     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			     Map hints = Maps.newHashMap();
				hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			     hints.put(EncodeHintType.MARGIN, 0);
			     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height,hints);
			     System.out.println(path+fileName.concat(".").concat(format));
			     File file1 = new File(path,fileName.concat(".").concat(format));
			     MatrixToImageWriter.writeToPath(bitMatrix, format, file1.toPath());
			     return true;
			 } catch (Exception e) {
				 System.out.println("生成二维码失败！！");
			     e.printStackTrace();
			     return false;
			 }
	  }
	  
     /**
      * 生成条形码 
      * @param contents 条形码保存的内容
      * @param width 条形码宽
      * @param height 条形码高
      * @param imgPath 条形码图片路径
      * @param format 条形码图片格式
      * @param fileName 条形码文件名称
      * @return
      */
    public static boolean createBarCode(String contents, int width, int height, String imgPath,String fileName,String format) {    
        try {    
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,    
            		BarcodeFormat.CODE_128, width, height, null);    
            Path path = new File(imgPath,fileName.concat(".").concat(format)).toPath();  
            MatrixToImageWriter.writeToPath(bitMatrix,format,path);  
            return true;
        } catch (Exception e) {    
            e.printStackTrace();    
            return false;
        }    
    }    
}
