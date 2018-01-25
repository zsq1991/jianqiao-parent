package com;

import com.alibaba.boot.dubbo.annotation.EnableDubboConsumerConfiguration;
import com.common.util.spring.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author system
 */
@ServletComponentScan
@SpringBootApplication
@EnableDubboConsumerConfiguration
public class ShiroPlatformApplication {

	/**
	 * 启动程序的入口
	 * @param args
	 */ 
	public static void main(String[] args){
		SpringContextUtil.setApplicationContext(SpringApplication.run(ShiroPlatformApplication.class, args));
	}

}