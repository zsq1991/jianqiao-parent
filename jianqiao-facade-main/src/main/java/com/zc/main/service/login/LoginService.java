package com.zc.main.service.login;

import com.zc.common.core.result.Result;

import java.util.Map;

/**
 * @Description : 用户登录
 * @author  by : tenghui
 * @Creation Date ：2018年01月18日13:41
 */
public interface LoginService {

    /**
     * @description ：密码登录
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/18 13:41
     * @version : 1.0.0
     */
    public Result loginPhoneAndPassword( Map<String,Object> params );
    /**
     * @description ：验证码登录
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/18 13:41
     * @version : 1.0.0
     */
    public Result loginPhoneAndCode( Map<String,Object> params );
}
