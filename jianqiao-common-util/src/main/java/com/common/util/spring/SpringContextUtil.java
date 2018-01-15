/**
 * Project Name:cc-common-util
 * File Name:SpringContextUtil.java
 * Package Name:com.common.util.spring
 * Date:2017年9月15日下午4:33:47
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.common.util.spring;

import org.springframework.context.ApplicationContext;

/**
 * ClassName:SpringContextUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年9月15日 下午4:33:47 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class SpringContextUtil {
    
	  private static ApplicationContext applicationContext;  
	
	  //获取上下文  
	  public static ApplicationContext getApplicationContext() {  
	      return applicationContext;  
	  }  
	
	  //设置上下文  
	  public static void setApplicationContext(ApplicationContext applicationContext) {  
	      SpringContextUtil.applicationContext = applicationContext;  
	  }  
	
	  //通过名字获取上下文中的bean  
	  public static Object getBean(String name){  
	      return applicationContext.getBean(name);  
	  }  
	    
	  //通过类型获取上下文中的bean  
	  public static Object getBean(Class<?> requiredType){  
	      return applicationContext.getBean(requiredType);  
	  }  
}
