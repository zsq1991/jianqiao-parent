package com.alqsoft.config.database;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * 初始化BaseMapper
 * @author by : whl
 * @package : com.ph.shopping.config.database
 * @progect : tianyi-parent
 * @Description :
 * @Creation Date ：2017年12月29日12:45
 */
@Configuration
@AutoConfigureAfter(DatabaseConfiguration.class)
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.zc.mybatis.dao");
        Properties properties = new Properties();
        properties.setProperty("mappers", "com.zc.common.core.basemapper.BaseMapper");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}
