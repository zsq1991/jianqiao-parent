package com.zc.main.controller.main.mobile.view.securitycode;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.common.util.ip.IPUtil;
import com.common.util.securitycode.SecurityCount;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.service.securitycode.SecurityCodeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description : 登录防刷——图片验证码
 * @Created by : tenghui
 * @Creation Date ：2018年01月16日11:19
 */
@Controller
@RequestMapping("mobile/view/checkimagecount")
public class SecurityCodeController {

    private static Logger logger = LoggerFactory.getLogger(SecurityCodeController.class);

    @DubboConsumer( version = "1.0.0" , timeout = 30000)
    private SecurityCodeService securityCodeService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Result result = new Result();

    /**
     * @description ：发送图形验证码
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/16 13:44
     * @version : 1.0.0
     * @param type 12是健桥 在路径写死
     * @param uniquenessCode 登录所用唯一验证码
     * @param response
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/securitycodeimg/{type}")
    @ResponseBody
    @CrossOrigin
    public Result securityCodeImg( @PathVariable String type, String uniquenessCode,HttpServletResponse response )
            throws InterruptedException{
        logger.info("============进入发送图形验证码方法==============");
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        logger.info("校验参数,传入参数:type是"+ type+"，==> uniquenessCode是" + uniquenessCode);
        if(StringUtils.isBlank(uniquenessCode)){
            result.setCode(0);
            result.setMsg("请执行正确操作");
            return result;
        }
        if (StringUtils.isBlank(type)) {
            return ResultUtils.returnError("请执行正确操作");
        }
        String code = "";
        try {
            logger.info("=======获取图片验证码的值======");
            code = SecurityCount.outputVerifyImage(100, 30, response.getOutputStream(), 4);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("==========获取图片验证码的值异常!============");
            return ResultUtils.returnError("网络繁忙");
        }
        logger.info("==============验证码的值是：" + code);
        logger.info("==========拼接参数,准备储存redis================");
        StringBuffer key = new StringBuffer("validateImage-").append(":").append(uniquenessCode).append(":").append(type);
        Long codeTime = 300L;
        try {
            logger.info("============开始存储redis===============");
            redisTemplate.opsForValue().set(key.toString(), code);
            redisTemplate.expire(key.toString(), codeTime, TimeUnit.SECONDS);
            logger.info("============存储redis结束===============");
        } catch (Exception e) {
            logger.info("uniquenessCode:"+ uniquenessCode + "type:"+ type +"redis存储异常");
            e.printStackTrace();
        }
        logger.info("=============发送图形验证码方法结束==============");
        return ResultUtils.returnSuccess("图片验证码返回成功");
    }

    /**
     * @description ：验证图形验证码是否正确
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/16 17:16
     * @version : 1.0.0
     * @param uniquenessCode  登录所用唯一验证码
     * @param type  12是健桥 在路径写死
     * @param code 验证码
     * @param request
     * @param phone 发送短信的手机号
     * @return
     */
    @RequestMapping("checkcode/{type}")
    @ResponseBody
    @CrossOrigin
    public Result checkCodeImage(String uniquenessCode, @PathVariable String type, String code, HttpServletRequest request, @RequestParam(value = "phone") String phone){
        logger.info("================进入验证图形验证码方法=================");
        logger.info("================uniquenessCode是:" + uniquenessCode);
        if(StringUtils.isBlank(uniquenessCode)){
            result.setCode(0);
            result.setMsg("请执行正确操作");
            return result;
        }
        if(StringUtils.isBlank(type)){
            result.setCode(0);
            result.setMsg("请执行正确操作");
            return result;
        }
        if(StringUtils.isBlank(code)){
            result.setCode(0);
            result.setMsg("请输入验证码");
            return result;
        }
        StringBuffer key = new StringBuffer("validateImage-").append(":").append(uniquenessCode).append(":").append(type);
        String RedisCode = null;
        try {
            RedisCode = redisTemplate.opsForValue().get(key.toString());
            redisTemplate.delete(key.toString());
        } catch (Exception e) {
            logger.info("imageUniquenessCode:" + uniquenessCode + "type:" + type + "code:" + code + "获取redis中验证码失败");
            return ResultUtils.returnError("验证异常");
        }
        if(StringUtils.isBlank(RedisCode)){
            result.setCode(0);
            result.setMsg("验证码已过期，请从新操作");
            logger.info("================验证图形验证码方法结束=================");
            return result;
        }else if(RedisCode.toString().equals(code)){
            result.setCode(1);
            result.setMsg("验证成功");
            String codeType = "JQ2017613";
            String ipAddress = IPUtil.getIpAddress(request);
            logger.info("------图形验证成功,调用发送短信验证码接口，用户IP地址："+ipAddress+"-------------");
            Result result = securityCodeService.sendCodeRegisterByPhone(phone,codeType);
            return result;
        }
        result.setCode(0);
        result.setMsg("验证失败");
        logger.info("================验证图形验证码方法结束=================");
        return result;
    }



}
