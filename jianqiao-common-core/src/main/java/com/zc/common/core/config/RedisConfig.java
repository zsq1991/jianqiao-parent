package com.zc.common.core.config;

import com.zc.common.core.init.InitParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

/**
 * @author xwolf
 * @since 1.8
 **/
@PropertySource(value ="classpath:redis.properties")
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}
