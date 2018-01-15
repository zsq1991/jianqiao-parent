/**
 * Project Name:cc-common-config
 * File Name:SpringProperties.java
 * Package Name:com.zc.common.config.spring
 * Date:2017年9月6日下午6:04:05
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.zc.common.config.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * ClassName:SpringProperties <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年9月6日 下午6:04:05 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Configuration
@PropertySource(value = "classpath:spring.properties")
@ConfigurationProperties(prefix="StandardPasswordEncoder")
public class SpringProperties {
	
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
