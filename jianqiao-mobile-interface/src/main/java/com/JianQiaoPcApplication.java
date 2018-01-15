package com;

import com.alibaba.boot.dubbo.annotation.EnableDubboConsumerConfiguration;
import com.common.util.spring.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Project Name:cc-server-main
 * File Name:HehuoApplication.java
 * Package Name:
 * Date:2017年8月21日下午8:32:22
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/

/**
 * ClassName:MainApplication <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年8月21日 下午8:32:22 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@ServletComponentScan
@SpringBootApplication
@EnableDubboConsumerConfiguration
public class JianQiaoPcApplication {
	/**
	 * 启动程序的入口
	 * @param args
	 */ 
	public static void main(String[] args){
		SpringContextUtil.setApplicationContext(SpringApplication.run(JianQiaoPcApplication.class, args));
	}
}
