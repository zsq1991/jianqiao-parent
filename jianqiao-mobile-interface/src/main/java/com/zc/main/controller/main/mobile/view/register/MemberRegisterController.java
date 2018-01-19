package com.zc.main.controller.main.mobile.view.register;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.result.Result;
import com.zc.main.service.register.MemberRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description : 用户注册
 * @Created by : tenghui
 * @Creation Date ：2018年01月17日13:39
 */
@Controller
@RequestMapping("mobile/view/member")
public class MemberRegisterController {

    @DubboConsumer( version = "1.0.0" , timeout = 30000)
    private MemberRegisterService memberRegisterService;

    /**
     * @description ：用户注册
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/18 15:52
     * @version : 1.0.0
     * @param phone 手机号
     * @param password 密码
     * @param imageCode 验证码
     * @param twicePassword 二次密码
     * @param request
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public Result memberRegisterByPhone(@RequestParam(value = "phone") String phone,
                                        @RequestParam(value = "password") String password,
                                        @RequestParam(value = "imageCode") String imageCode,
                                        @RequestParam(value = "twicePassword") String twicePassword,
                                        HttpServletRequest request
                                        ){
        String ip = getClientIp(request);
        String agent = request.getHeader("User-Agent");
        Map<String,Object> params = new HashMap<String,Object>(16);
        params.put("ip",ip);
        params.put("agent",agent);
        params.put("phone",phone);
        params.put("password",password);
        params.put("imageCode",imageCode);
        params.put("twicePassword",twicePassword);
        Result result = memberRegisterService.memberRegisterByPhone(params);

        return result;

    }

    /**
     * @description ：修改密码
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/18 16:19
     * @version : 1.0.0
     * @param phone 手机号
     * @param password 密码
     * @param passwordR 确认密码
     * @param imageCode 验证码
     * @param request
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "updatepassword",method = RequestMethod.POST)
    @ResponseBody()
    public Result updatePasswordByPhone(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "passwordR") String passwordR,
            @RequestParam(value = "imageCode") String imageCode,
            HttpServletRequest request){
        Map<String,Object> params = new HashMap<String,Object>(16);
        params.put("phone",phone);
        params.put("password",password);
        params.put("passwordR",passwordR);
        params.put("imageCode",imageCode);
        Result result = memberRegisterService.updatePasswordByPhone(params);
        return result;

    }
    /**
     * @description ：获取客户端的真实ip
     *  clientIp 从request域中取出进行判断(代理上网)XFF，XRI，Addr, 如果 forwarded_for 设成了
     *  off,则：X-Forwarded-For: unknown
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/17 13:59
     * @version : 1.0.0
     */
    public String getClientIp(HttpServletRequest request) {

        String key = "unknown";
        String key1 = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || key.equalsIgnoreCase(ip) || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || key.equalsIgnoreCase(ip) || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || key.equalsIgnoreCase(key1) || ip.length() == 0) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
