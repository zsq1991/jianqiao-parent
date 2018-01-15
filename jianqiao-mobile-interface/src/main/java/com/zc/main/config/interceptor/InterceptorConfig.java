package com.zc.main.config.interceptor;

import com.zc.common.core.init.InitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xwolf
 * @since 1.8
 **/
//@PropertySource(value ="classpath:init.properties")
@Configuration
public class InterceptorConfig{

    @Autowired
    private InitParams initParams;

    //@Value("${mark}")
    private String mark;

    //@Value("${publicurl}")
    private String publicurl;

    private String[] publicurls;

    public String getMark() {
        return initParams.getProperties().getProperty("mark");
    }

    public String[] getPublicurl() {
        publicurl = initParams.getProperties().getProperty("publicurl");
        if(!publicurl.equals(null)) {
            publicurls = publicurl.split(",");
        }
        return publicurls;
    }
}
