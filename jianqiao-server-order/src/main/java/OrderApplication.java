import com.alibaba.boot.dubbo.annotation.EnableDubboConsumerConfiguration;
import com.alibaba.boot.dubbo.annotation.EnableDubboProviderConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Project Name:cc-server-main
 * File Name:MainApplication.java
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
@SpringBootApplication(scanBasePackages = {"com.codingapi.tx","com.zc","com.alqsoft","com.alibaba"})
@EnableDubboProviderConfiguration
@EnableDubboConsumerConfiguration
@MapperScan(basePackages = "com.zc.mybatis.dao")
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@EnableTransactionManagement(order = 2)
public class OrderApplication {


	/**
	 * 启动程序的入口
	 * @param args
	 */
	public static void main(String[] args){
		SpringApplication.run(OrderApplication.class, args);
	}
}
