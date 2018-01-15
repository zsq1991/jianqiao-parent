package com.zc;

import com.alibaba.boot.dubbo.annotation.EnableDubboConsumerConfiguration;
import com.alibaba.boot.dubbo.annotation.EnableDubboProviderConfiguration;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(scanBasePackages = {"com.codingapi.tx","com.zc","com.alqsoft","com.alibaba"})
@EnableDubboProviderConfiguration
@EnableDubboConsumerConfiguration
@MapperScan(basePackages = "com.zc.mybatis.dao",annotationClass= MyBatisRepository.class)
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@EnableTransactionManagement(order = 2)
public class ShiroServerApplication {


	/**
	 * 启动程序的入口
	 * @param args
	 */
	public static void main(String[] args){
		SpringApplication.run(ShiroServerApplication.class, args);
	} 

}
