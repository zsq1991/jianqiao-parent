package com.zc.common.core.json.dwz;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.zc.common.core.utils.MyObjectUtils;
/**
 * DWZ返回json的类
 * 
 * @author zhangkaoqin
 * @e-mail 15160035470@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2011-12-20 晚上:22:08
 * 
 */
public class DWZJson {
	
	private DWZJson(){
		throw new AssertionError();
	}
	/**
	 * DWZ框架的select联级json的list转换
	 * @param objects
	 * @param key  键值
	 * @param value   内容
	 * @return   json的String结果
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("rawtypes")
	public static String dwzSelectJson(List objects,String key,String value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		StringWriter stringWriter=new StringWriter();
		stringWriter.append("[");
		int i=0;
		int j=objects.size();
		for (Object object : objects) {
			i++;
			stringWriter.append("[");
			stringWriter.append("\"");
			stringWriter.append(MyObjectUtils.toString(BeanUtils.getProperty(object,key)));
			stringWriter.append("\"");
		    stringWriter.append(",");
		    stringWriter.append("\"");
		    stringWriter.append(MyObjectUtils.toString(BeanUtils.getProperty(object,value)));
		    stringWriter.append("\"");
			stringWriter.append("]");
			if(i<j){
			stringWriter.append(",");
			}
		}
		stringWriter.append("]");
		return stringWriter.toString();
	}
}
