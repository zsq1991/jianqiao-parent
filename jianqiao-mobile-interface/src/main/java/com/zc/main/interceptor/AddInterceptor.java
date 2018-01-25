package com.zc.main.interceptor;

import com.zc.common.core.interceptor.AvoidDuplicateSubmissionInterceptor;
import com.zc.main.config.interceptor.InterceptorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @description 添加拦截器
 * @author system
 * @date 2018-01-24 14:06
 * @version 1.0.0
 */
@Configuration
public class AddInterceptor extends WebMvcConfigurerAdapter {
    @Autowired
    private InterceptorConfig interceptorConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry){


          //PC端token防爆拦截器


//         registry.addInterceptor(new AvoidDuplicateSubmissionInterceptor());
    }
}
