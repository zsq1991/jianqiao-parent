package com.zc.main.controller.main.mobile.view.login;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.result.Result;
import com.zc.main.service.login.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
     * @Creation Date ： 2018/1/18 13:39
     * @version : 1.0.0
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
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("phone",phone);
        params.put("password",password);
        params.put("agent",agent);
        Result result = loginService.loginPhoneAndPassword(params);
        return result;
    }


}
