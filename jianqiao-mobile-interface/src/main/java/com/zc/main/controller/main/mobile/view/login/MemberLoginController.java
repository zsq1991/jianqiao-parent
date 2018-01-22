package com.zc.main.controller.main.mobile.view.login;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.google.common.collect.Maps;
import com.zc.common.core.result.Result;
import com.zc.main.service.login.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description : 用户登录
 * @Created by : tenghui
 * @Creation Date ：2018年01月18日13:38
 */
@Controller
@RequestMapping("mobile/view/login")
public class MemberLoginController {

    @DubboConsumer( version = "1.0.0" , timeout = 30000)
    private LoginService loginService;

    /**
     * @description ：用户登录 使用密码登录
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/18 14:34
     * @version : 1.0.0
     * @param phone 手机号
     * @param password 密码
     * @param request
     * @return
     */
    @RequestMapping(value = "password", method = RequestMethod.POST)
    @ResponseBody
    public Result loginPhoneAndPassword(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "password") String password,
            HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        Map<String,Object> params = Maps.newHashMap();
        params.put("phone",phone);
        params.put("password",password);
        params.put("agent",agent);
        Result result = loginService.loginPhoneAndPassword(params);
        return result;
    }

    /**
     * @description ：验证码登录
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/18 15:06
     * @version : 1.0.0
     * @param phone
     * @param code
     * @param request
     * @return
     */
    @RequestMapping(value = "imagecode", method = RequestMethod.POST)
    @ResponseBody
    public Result loginPhoneAndCode(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "code") String code,
            HttpServletRequest request){
        Map<String,Object> params = Maps.newHashMap();
        params.put("phone",phone);
        params.put("code",code);
        Result result = loginService.loginPhoneAndCode(params);
        return result;
    }

}
