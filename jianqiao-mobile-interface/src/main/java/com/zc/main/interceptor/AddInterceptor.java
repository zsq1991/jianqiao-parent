package com.zc.main.interceptor;

import com.zc.common.core.interceptor.AvoidDuplicateSubmissionInterceptor;
import com.zc.main.config.interceptor.InterceptorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@Configuration
public class AddInterceptor extends WebMvcConfigurerAdapter {
    @Autowired
    private InterceptorConfig interceptorConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry){


          //PC端token防爆拦截器


         registry.addInterceptor(new AvoidDuplicateSubmissionInterceptor());
    }
}
