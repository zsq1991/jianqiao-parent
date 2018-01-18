package com.zc.service.impl.login;

import com.alibaba.dubbo.config.annotation.Service;
import com.mysql.jdbc.StringUtils;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.common.core.utils.MD5Util;
import com.zc.main.entity.member.Member;
import com.zc.main.service.login.LoginService;
import com.zc.mybatis.dao.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description : 用户登录
 * @Created by : tenghui
 * @Creation Date ：2018年01月18日13:42
 */
@Component
@Transactional(readOnly = true)
@Service(version = "1.0.0",interfaceClass=LoginService.class)
public class LoginServiceImpl implements LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Result loginPhoneAndPassword(Map<String,Object> params) {
        logger.info("=========进入密码登录方法==========");
        Result result = new Result();
        Map map = new HashMap();
        String address = null;
        String isDelete = "0";
        String userbrowser = "未知";
        logger.info("=========获取参数并校验==========");
        if( ObjectUtils.isEmpty(params.get("phone")) ){
            return ResultUtils.returnError("参数异常");
        }
        if( ObjectUtils.isEmpty(params.get("password")) ){
            return ResultUtils.returnError("参数异常");
        }
        if( ObjectUtils.isEmpty(params.get("agent")) ){
            return ResultUtils.returnError("参数异常");
        }
        String phone = params.get("phone").toString();
        String password = params.get("password").toString();
        // 获取浏览器
        String agent = params.get("agent").toString();
        // 获取ipgetClientIp
//        String clientIP = params.get("ip").toString();

        if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
            return ResultUtils.returnError("手机号不合法");
        }
        if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
            return ResultUtils.returnError("密码格式有误");
        } else if (password.length() > 12 || password.length() < 6) {
            return ResultUtils.returnError("密码格式有误");
        }
        if (null != agent) {
            if (agent.contains("Chrome")) {
                userbrowser = "Chrome";
            } else if (agent.contains("Firefox")) {
                userbrowser = "Firefox";
            } else if (agent.contains("Safari")) {
                userbrowser = "Safari";
            } else if (agent.contains("Trident")) {
                userbrowser = "Trident";
            } else if (agent.contains("IE")) {
                userbrowser = "IE";
            } else if (agent.contains("360")) {
                userbrowser = "360";
            }
        }
        logger.info("=========查询用户是否存在==========");
        Member loginData = memberMapper.getMemberByPhone(phone);

        if (loginData != null) {
            logger.info("=========用户存在,进行密码匹配==========");
            isDelete = loginData.getIsDelete() == null ? "0" : loginData.getIsDelete().toString();
            if (isDelete.equals("1")) {
                logger.info("=========密码登录方法结束==========");
                return ResultUtils.returnError("该用户已禁用请联系客服");
            }
            // 加密密码
            String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + loginData.getUuid(), "utf-8");

            if (loginData.getPassword().equals(passwordMD5)) {
                logger.info("=========记录登录信息，用户id,登录的次数count,updateTime,creteaIp,sessionId==========");
                if (loginData.getLogoAttachmentId() != null) {
                    String number = loginData.getLogoAttachmentId() == null ? "flase" : "true";
                    if (number.equals("true")) {
                        address = memberMapper.getAddressByPhone(loginData.getLogoAttachmentId());
                    }
                }
                try {
                    map.put("id", loginData.getId());
                    map.put("uuid", loginData.getUuid());
                    map.put("phone", loginData.getPhone());
                    map.put("address", address);
                    Integer number=	loginData.getStatus()==null?0:loginData.getStatus();
                    map.put("status", number);
                    map.put("nickname", loginData.getNickname());
                    String userType = loginData.getUserType() == null ? "0" : loginData.getUserType().toString();
                    map.put("userType", userType);// 0普通 1认证后用户可以发布访谈 口述
                    result.setCode(1);
                    result.setMsg("登录成功");
                    result.setContent(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("=========密码登录方法结束,异常==========");
                    result.setCode(0);
                    result.setMsg("接口调用失败");
                }

            } else {
                logger.info("=========密码登录方法结束==========");
                return ResultUtils.returnError("密码错误，请输入正确的密码");
            }
        } else {
            logger.info("=========密码登录方法结束,失败==========");
            return ResultUtils.returnError("该手机号未注册，请先注册",-620);
        }
        logger.info("=========密码登录方法结束,成功==========");
        return result;
    }

    @Override
    public Result loginPhoneAndCode(HttpServletRequest request, String codeType) {
        return null;
    }
}
