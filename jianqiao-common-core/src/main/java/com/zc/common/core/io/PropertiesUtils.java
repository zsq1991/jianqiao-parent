package com.zc.common.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 
 * 获取配置中定义的属性
 * @author czb
 * @e-mail chengzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-7-1 下午5:08:24
 * 
 */

public class PropertiesUtils {
	/**
	 * 初始化加载配置文件
	 */
    private static Properties properties= new Properties();
	/**
	 * 读取Properties文件的例子 
	 * @param class1 所在的当前类
	 * @param filePath 文件的路径
	 * @return
	 */
	public static <T> Properties getProperties(Class<T> class1,String filePath){
		try {
			properties.load(class1.getResourceAsStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	
	/**
	 * 
	 * 方法的作用:采用Properties类取得属性文件对应值.<br/>
	 * 
	 * @param propertiesFileName  文件名，如a.properties
	 * @param propertyName 属性名
	 * @return  据属性名得到的属性值，如没有返回""
	 * 
	 * @author mike
	 * @create-time 2014-1-3 下午3:05:15
	 */
	 public static String getValueByPropertyName(String propertiesFileName,String propertyName) {
	        String s="";
	        FileInputStream in;
	        try {
	            //propertiesFileName如test.properties
				//以流的形式读入属性文件
	            in = new FileInputStream(propertiesFileName);
				//属性文件将该流加入的可被读取的属性中
	            properties.load(in);
				//读完了关闭
	            in.close();
				//取得对应的属性值
	            s=properties.getProperty(propertyName);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return s;
	    }
	 
	 
	 
	 /**
	  * 采用ResourceBundel类取得属性文件对应值，这个只能够读取，不可以更改及新增的属性
	  * 方法的作用:.<br/>
	  * @param propertiesFileNameWithoutPostfix 文件名，不带后缀
	  * @param propertyName  属性名
	  * @return 根据属性名得到的属性值，如没有返回""
	  * 
	  * @author mike
	  * @create-time 2014-1-3 下午3:06:53
	  */
	 public static String getValueByPropertyNames(String propertiesFileNameWithoutPostfix,String propertyName) {
	        String s="";
	        //如属性文件是test.properties，那此时propertiesFileNameWithoutPostfix的值就是test
	        ResourceBundle bundes = ResourceBundle.getBundle(propertiesFileNameWithoutPostfix);
	        s=bundes.getString(propertyName);
	        return s;
	    }
	 
	 
	 /**
	  * 
	  * 方法的作用:更改属性文件的值，如果对应的属性不存在，则自动增加该属性.<br/>
	  * 
	  * @param propertiesFileName  文件名，如a.properties
	  * @param propertyName 属性名
	  * @param propertyValue要修改的属性值
	  * @return 是否操作成功
	  * 
	  * @author mike
	  * @create-time 2014-1-3 下午3:08:14
	  */
	 public static boolean changeValueByPropertyName(String propertiesFileName,String propertyName,String propertyValue) {
	        boolean writeOK=false;
	        FileInputStream in;
	        try {
	            in = new FileInputStream(propertiesFileName);
	            properties.load(in);
	            in.close();
				//设置属性值，如果该属性不存在，则新增
	            properties.setProperty(propertyName,propertyValue);
				//输出流
	            FileOutputStream out=new FileOutputStream(propertiesFileName);
				//设置属性头，如不想设置，请把后面一个用""替换掉
	            properties.store(out,"send email status");
				//清空缓存，写入磁盘
	            out.flush();
				//关闭输出流
	            out.close();
	            writeOK=true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return writeOK;
	    }
	 
	 
	 
	 /**
	  * 
	  * 方法的作用:初始化配置文件.<br/>
	  * 
	  * @param class1 类
	  * @param propertiesFileName 配置文件名
	  * @return  InputStream
	  * 
	  * @author mike
	  * @create-time 2014-1-6 上午10:39:57
	  */
	 private static <T> void initProperties(Class<T> class1,String propertiesFileName){
	     try {
	         InputStream  ips = class1.getResourceAsStream(propertiesFileName);
	            properties.load(ips);
	            ips.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }

	 /**
	  * 
	  * 方法的作用:获取属性key对应的值.<br/>
	  * 
	  * @param class1  当前类
	  * @param propertiesFileName  配置文件
	  * @param propertyName 属性名
	  * @return
	  * 
	  * @author mike
	  * @create-time 2014-1-6 上午10:47:12
	  */
	 public static <T> String getValueByPropertyName(Class<T> class1,String propertiesFileName,String propertyName){
	     if (properties.isEmpty()) {
	         initProperties(class1, propertiesFileName);
	     }
	    return properties.getProperty(propertyName);
	 }
	 
	 
	 /**
	  * 
	  * 方法的作用:修改配置文件属性对应的属性值.<br/>
	  * 
	  * @param class1 当前类
	  * @param propertiesFileName 配置文件
	  * @param propertyName 文件属性
	  * @param propertyValue 要修改的属性值
	  * @return
	  * 
	  * @author mike
	  * @create-time 2014-1-6 上午10:53:50
	  */
	 public static <T> boolean updatePropertiesByProName(Class<T> class1,String propertiesFileName,String propertyName,String propertyValue){
	     boolean writeOK=false;
	     if(properties.isEmpty()) {
             initProperties(class1, propertiesFileName);
         }
	        //修改值
	        properties.setProperty(propertyName,propertyValue);
	        //保存文件
	        try {
				//得到文件路径
	            URL fileUrl =class1.getResource(propertiesFileName);
	            FileOutputStream fos = new FileOutputStream(new File(fileUrl.toURI()));
	            properties.store(fos, "send email status");
	            fos.close();
	            writeOK=true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return writeOK;
	 }
	 
}
