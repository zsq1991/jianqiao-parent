package com;

import com.alibaba.boot.dubbo.annotation.EnableDubboConsumerConfiguration;
import com.alibaba.boot.dubbo.annotation.EnableDubboProviderConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ClassName: MemberServerApplication
 * Date:      2018/1/15 17:24
 * @author   tenghui
 * @version  1.0.0
 * @since    JDK 1.8
 * @see
 */
@SpringBootApplication(scanBasePackages = {"com.codingapi.tx","com.zc","com.alqsoft","com.alibaba"})
@EnableDubboProviderConfiguration
@EnableDubboConsumerConfiguration
@MapperScan(basePackages = "com.zc.mybatis.dao")
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@EnableTransactionManagement(order = 2)
public class MemberServerApplication {


	/**
	 * 启动程序的入口
	 * @param args
	 */
	public static void main(String[] args){
		SpringApplication.run(MemberServerApplication.class, args);
	}
}
