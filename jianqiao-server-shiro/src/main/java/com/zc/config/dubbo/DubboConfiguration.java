package com.zc.config.dubbo;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.rpc.Invoker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * @项目：
 *
 * @描述：dubbo消费者配置
 *
 */
@Configuration
@ConditionalOnClass(Invoker.class)
@PropertySource(value = "classpath:dubbo.properties")
public class DubboConfiguration {

	@Value("${spring.dubbo.appname}")
	private String applicationName;

	@Value("${spring.dubbo.protocol}")
	private String protocol;

	@Value("${spring.dubbo.registry}")
	private String registry;

	@Value("${spring.dubbo.timeout}")
	private int timeout;

	@Value("${spring.dubbo.retries}")
	private int retries;


	@Value("${spring.dubbo.port}")
	private int port;


	@Value("${spring.dubbo.delay}")
	private Integer delay;

	@Value("${spring.dubbo.filter}")
	private String filter;


	/**
	 * 注入dubbo上下文
	 * 
	 * @return
	 */
	@Bean
	public ApplicationConfig applicationConfig() {
		// 当前应用配置
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(this.applicationName);
		applicationConfig.setLogger("slf4j");
		return applicationConfig;
	}

	/**
	 * 注入dubbo注册中心配置,基于zookeeper
	 * 
	 * @return
	 */
	@Bean
	public RegistryConfig registryConfig() {
		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setProtocol(this.protocol);
		registry.setAddress(this.registry);
		// 设置启动时不检查注册中心
		registry.setCheck(false);
		registry.setPort(port);
		return registry;
	}
	
	/**
	 * dubbo监控中心
	 * @return
	 */
	@Bean
	public MonitorConfig monitorConfig() {
		MonitorConfig mc = new MonitorConfig();
		mc.setProtocol("registry");
		return mc;
	}

	/**
	 * 默认基于dubbo协议提供服务
	 *
	 * @return
	 */
	@Bean
	public ProtocolConfig protocolConfig() {
		// 服务提供者协议配置
		ProtocolConfig protocolConfig = new ProtocolConfig();
		protocolConfig.setName(protocol);
		protocolConfig.setPort(port);
		protocolConfig.setThreads(200);
		return protocolConfig;
	}

	/**
	 * dubbo服务提供
	 *
	 * @param applicationConfig
	 * @param registryConfig
	 * @param protocolConfig
	 * @return
	 */
	@Bean(name = "shiroProvider")
	public ProviderConfig providerConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig,
										 ProtocolConfig protocolConfig, MonitorConfig monitorConfig) {
		ProviderConfig providerConfig = new ProviderConfig();
		providerConfig.setTimeout(timeout);
		providerConfig.setRetries(retries);
		providerConfig.setDelay(delay);
		providerConfig.setApplication(applicationConfig);
		providerConfig.setRegistry(registryConfig);
		providerConfig.setProtocol(protocolConfig);
		providerConfig.setMonitor(monitorConfig);
		return providerConfig;
	}

	/**
	 * dubbo消费
	 *
	 * @param applicationConfig
	 * @param registryConfig
	 * @param monitorConfig
	 * @return
	 */
	@Bean(name="shiroConsumer")
	public ConsumerConfig consumerConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig, MonitorConfig monitorConfig) {
		ConsumerConfig consumer = new ConsumerConfig();
		consumer.setApplication(applicationConfig);
		consumer.setRegistry(registryConfig);
		consumer.setMonitor(monitorConfig);
		//设置不检查服务提供者
		consumer.setCheck(false);
		consumer.setFilter(filter);
		return consumer;
	}

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @param applicationName
	 *            the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol
	 *            the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}


	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}
}
