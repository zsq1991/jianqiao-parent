/**
 * Project Name:cc-web-main
 * File Name:SpringMvcConfig.java
 * Package Name:com.zc.main.config.springmvc
 * Date:2017年8月31日下午2:53:58
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.zc.common.config.springmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:SpringMvcConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年8月31日 下午2:53:58 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter{
	  /**
     * 不需要拦截处理的URI
     */
    public static final String[] IGNORE_URIS = {
            "/error/**",
            "/kaptcha/**",
            "/auth/**",
            "/login",
            "/signin/**",
            "/signup/**",
             "*"
    };
    
    /**
     * 过滤资源
     */
    public static final String[] IGNORE_RESOURCES = {
    		//"/static/**"
    };
    /**
     * 配置HttpMessageConverter
     * @return
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
    	List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
    	MediaType meidaType = new MediaType(MediaType.TEXT_PLAIN,Charset.forName("utf-8"));
    	supportedMediaTypes.add(meidaType);
    	mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(mappingJackson2HttpMessageConverter);
        converters.add(stringHttpMessageConverter);
    }

    /**
     * 静态资源拦截器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**","/favicon.ico").addResourceLocations("classpath:/static/","/favicon.ico");
        super.addResourceHandlers(registry);
    }
}
