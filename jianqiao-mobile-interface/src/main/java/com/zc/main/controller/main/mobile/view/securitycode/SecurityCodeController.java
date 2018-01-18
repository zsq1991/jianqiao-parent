package com.zc.main.controller.main.mobile.view.securitycode;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.result.Result;
import com.zc.main.service.securitycode.SecurityCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description : 短信验证码
 * @Created by : tenghui
 * @Creation Date ：2018年01月18日15:16
 */
@Controller
@RequestMapping("mobile/view/securitycode")
public class SecurityCodeController {

    private static Logger logger = LoggerFactory.getLogger(SecurityCodeController.class);

    @DubboConsumer( version = "1.0.0" , timeout = 30000)
    private SecurityCodeService securityCodeService;

    /**
     * @description ：发送验证码
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/18 15:18
     * @version : 1.0.0
     * @param phone 手机号
     * @return
     */
    @RequestMapping(value="loginsend",method= RequestMethod.POST)
    @ResponseBody
    public Result sendCodeLoginByPhone(@RequestParam(value="phone") String phone){
        String codeType="JQ2017613";
        Result result = securityCodeService.sendCodeLoginByPhone(phone,codeType);
        return result;
    }

    /**
     * @description ：检验验证码
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/18 15:43
     * @version : 1.0.0
     * @param phone
     * @param code
     * @return
     */
    @RequestMapping(value="registercheck",method=RequestMethod.POST)
    @ResponseBody
    public Result checkCodeRegisterByPhone(@RequestParam(value="phone") String phone,@RequestParam(value="code") String code){
        String codeType="JQ2017613";
        Result result = securityCodeService.checkMessageCode(phone,code,codeType);
        return result;

    }
    /**
     * @description ：用户注册，获取验证码
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/18 15:49
     * @version : 1.0.0
     * @param phone
     * @return
     */
    @RequestMapping(value="registersend",method=RequestMethod.POST)
    @ResponseBody
    public Result sendCodeRegisterByPhone(@RequestParam(value="phone") String phone){
        String codeType="JQ2017613";
        Result result = securityCodeService.sendCodeRegisterByPhone(phone,codeType);
        return result;
    }


}
