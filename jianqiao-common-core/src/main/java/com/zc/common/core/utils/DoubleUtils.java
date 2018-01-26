package com.zc.common.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * <p>
 * 浮点数精确运算计算器
 * </p>
 * 
 * @Package com.alqsoft.utils
 * @author 黄乡南
 * @e-mail 823798218@qq.com
 * @date 2014-9-29 下午5:37:39
 * @version V1.0
 */
public class DoubleUtils {
	public static double add(double val1, double val2) {
		BigDecimal b1 = new BigDecimal(Double.toString(val1));
		BigDecimal b2 = new BigDecimal(Double.toString(val2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return v1-v2的值
	 * 
	 */
	public static double subtract(double val1, double val2) {
		BigDecimal b1 = new BigDecimal(Double.toString(val1));
		BigDecimal b2 = new BigDecimal(Double.toString(val2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 * 
	 */

	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param val1
	 *            被除数
	 * @param val2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。必须满足scale>=0
	 * @return 两个参数的商
	 */

	public static double div(double val1, double val2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("scale 必须大于等于0");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(val1));
		BigDecimal b2 = new BigDecimal(Double.toString(val2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位 ,必须满足scale>=0
	 * @return 四舍五入后的结果
	 */

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"scale必须大于等于0");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 判断两个数字是否相等
	
	 * @Title: equals
	 * @Description: TODO
	 * @param: @param num1
	 * @param: @param num2
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 */
	public static boolean equals(double num1,double num2){
		return equals(num1,num2,null); 
	}
	
	/**
	 * 判断两个数字是否相等，并按保留几位小数进行比较，为null代表不按小数位四舍五入比较
	
	 * @Title: equals
	 * @Description: TODO
	 * @param: @param num1 数字1
	 * @param: @param num2 数字2
	 * @param: @param decimalNum 小数位
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 */
	public static boolean equals(double num1,double num2,Integer decimalNum){
		BigDecimal data1 = new BigDecimal(num1); 
		BigDecimal data2 = new BigDecimal(num2);
		if(decimalNum!=null){
			data1 = data1.setScale(decimalNum,BigDecimal.ROUND_HALF_UP);
			data2 = data2.setScale(decimalNum,BigDecimal.ROUND_HALF_UP);
		}
		return data1.compareTo(data2) == 0; 
	}
	
	public static double scaleDouble(double num,int decimalNum){
		return scaleDouble(num,decimalNum,BigDecimal.ROUND_HALF_UP);
	}
	
	public static double scaleDouble(double num,int decimalNum,int roundingMode){
		BigDecimal data1 = new BigDecimal(num); 
		
		data1 = data1.setScale(decimalNum+1,BigDecimal.ROUND_HALF_UP);
		
		return data1.setScale(decimalNum,roundingMode).doubleValue();
	}
	
	/**
	 * 精确到两位小数
	
	 * @Title: tworound
	 * @Description: TODO
	 * @param: @param d
	 * @param: @return   
	 * @return: double   
	 * @throws
	 */
	public static double tworound(double d){
		DecimalFormat pcpricedfc = new DecimalFormat("##.00");
		return Double.parseDouble(pcpricedfc.format(d));
	}
	/**
	 * 精确到scale位小数
	 * @param d
	 * @return
	 */
	public static String formatRound(double d,int scale){
		BigDecimal b = new BigDecimal(d);
		double f1 = b.setScale(5,BigDecimal.ROUND_HALF_UP).doubleValue();
		String fom = "0.0";
		int two = 2;
		int three = 3;
		int four = 4;
		int five = 5;
		if(scale == two){
			fom = "0.00";
		}else if(scale==three){
			fom = "0.000";
		}else if(scale==four){
			fom = "0.0000";
		}else if(scale==five){
			fom = "0.00000";
		}else{
//			保留1位
		}
		DecimalFormat decimalFormat = new DecimalFormat(fom);
		return decimalFormat.format(f1);
	}
}
