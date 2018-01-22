package com.zc.service.impl.login;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.mysql.jdbc.StringUtils;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.common.core.utils.MD5Util;
import com.zc.main.entity.member.Member;
import com.zc.main.service.login.LoginService;
import com.zc.main.service.securitycode.SecurityCodeService;
import com.zc.mybatis.dao.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @Description : 用户登录
 * @Created by : tenghui
 * @Creation Date ：2018年01月18日13:42
 */
@Component
@Transactional(readOnly = true,rollbackFor=Exception.class)
@Service(version = "1.0.0",interfaceClass=LoginService.class)
public class LoginServiceImpl implements LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private SecurityCodeService securityCodeService;

    @Override
    public Result loginPhoneAndPassword( Map<String,Object> params ) {
        logger.info("=========进入密码登录方法==========");
        Result result = new Result();
        Map map = Maps.newHashMap();
        String address = null;
        String isDelete = "0";
        String userbrowser = "未知";
        logger.info("=========获取参数并校验==========");
        if( ObjectUtils.isEmpty(params.get("phone")) ){
            logger.info("=========密码登录方法结束==========");
            return ResultUtils.returnError("参数异常");
        }
        if( ObjectUtils.isEmpty(params.get("password")) ){
            logger.info("=========密码登录方法结束==========");
            return ResultUtils.returnError("参数异常");
        }
        if( ObjectUtils.isEmpty(params.get("agent")) ){
            logger.info("=========密码登录方法结束==========");
            return ResultUtils.returnError("参数异常");
        }
        String phone = params.get("phone").toString();
        String password = params.get("password").toString();
        // 获取浏览器
        String agent = params.get("agent").toString();
        // 获取ipgetClientIp
//        String clientIP = params.get("ip").toString();

        if ( StringUtils.isEmptyOrWhitespaceOnly(phone) ) {
            logger.info("=========密码登录方法结束==========");
            return ResultUtils.returnError("手机号不合法");
        }
        if ( StringUtils.isEmptyOrWhitespaceOnly(password) ) {
            logger.info("=========密码登录方法结束==========");
            return ResultUtils.returnError("密码格式有误");
        } else if ( password.length() > 12 || password.length() < 6 ) {
            logger.info("=========密码登录方法结束==========");
            return ResultUtils.returnError("密码格式有误");
        }
        if ( null != agent ) {
            if ( agent.contains("Chrome") ) {
                userbrowser = "Chrome";
            } else if ( agent.contains("Firefox") ) {
                userbrowser = "Firefox";
            } else if ( agent.contains("Safari") ) {
                userbrowser = "Safari";
            } else if ( agent.contains("Trident") ) {
                userbrowser = "Trident";
            } else if ( agent.contains("IE") ) {
                userbrowser = "IE";
            } else if ( agent.contains("360") ) {
                userbrowser = "360";
            }
        }
        logger.info("=========查询用户是否存在==========");
        Member loginData = memberMapper.getMemberByPhone(phone);

        if (loginData != null) {
            logger.info("=========用户存在,进行密码匹配==========");
            isDelete = loginData.getIsDelete() == null ? "0" : loginData.getIsDelete().toString();
            if ("1".equals(isDelete)) {
                logger.info("=========密码登录方法结束==========");
                return ResultUtils.returnError("该用户已禁用请联系客服");
            }
            // 加密密码
            String passwordMD5 = MD5Util.getMD5Encode(MD5Util.getMD5Encode(password, "utf-8") + loginData.getUuid(), "utf-8");

            if ( loginData.getPassword().equals(passwordMD5) ) {
                logger.info("=========记录登录信息，用户id,登录的次数count,updateTime,creteaIp,sessionId==========");
                if ( loginData.getLogoAttachmentId() != null ) {
                    String number = loginData.getLogoAttachmentId() == null ? "flase" : "true";
                    if ("true".equals(number)) {
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
    public Result loginPhoneAndCode( Map<String,Object> params ) {
        logger.info("=========进入验证码登录方法==========");
        Result result = new Result();
        Map map = Maps.newHashMap();
        String codeType = "JQ2017613";
        String address = null;
        String isDelete ="0";
        logger.info("=========获取参数并校验==========");
        if( ObjectUtils.isEmpty(params.get("phone")) ){
            logger.info("=========验证码登录方法结束==========");
            return ResultUtils.returnError("参数异常");
        }
        if( ObjectUtils.isEmpty(params.get("code")) ){
            logger.info("=========验证码登录方法结束==========");
            return ResultUtils.returnError("参数异常");
        }
        String phone = params.get("phone").toString();
        String code = params.get("code").toString();

        // 获取验证码结果
        Result imageCode = securityCodeService.checkMessageCode(phone, code, codeType);

        // 通过手机号，查询是否有这个用户就查询这个用户
        if ( StringUtils.isEmptyOrWhitespaceOnly(phone) ) {
            logger.info("=========验证码登录方法结束==========");
            return ResultUtils.returnError("手机号不合法");
        } else if ( code == null ) {
            logger.info("=========验证码登录方法结束==========");
            return ResultUtils.returnError("验证码错误，请输入正确的验证码");
        }
        // 验证码
        if ( !(imageCode.getCode() == 1) ) {
            logger.info("=========验证码登录方法结束==========");
            return ResultUtils.returnError("验证码错误,请重新获取验证码");
        } else {
            logger.info("=========登录成功==========");
            Member loginData = memberMapper.getMemberByPhone(phone);

            if ( loginData != null ) {
                isDelete = loginData.getIsDelete() == null ? "0" : loginData.getIsDelete().toString();
                if ("1".equals(isDelete)) {
                    return ResultUtils.returnError("该用户已禁用请联系客服");
                }
                if ( loginData.getLogoAttachmentId() != null ) {
                    String number = loginData.getLogoAttachmentId() == null ? "flase" : "true";
                    if ("true".equals(number)) {
                        address = memberMapper.getAddressByPhone(loginData.getLogoAttachmentId());
                    }
                }

                map.put("id", loginData.getId());
                map.put("uuid", loginData.getUuid());
                map.put("phone", loginData.getPhone());
                map.put("address", address);
                Integer number = loginData.getStatus()==null?0:loginData.getStatus();
                map.put("status", number);
                map.put("nickname", loginData.getNickname());
                String userType = loginData.getUserType() == null ? "0" : loginData.getUserType().toString();
                map.put("userType", userType);// 0普通 1认证后用户可以发布访谈 口述
                result.setCode(1);
                result.setMsg("成功");
                result.setContent(map);
            }
        }
        logger.info("=========验证码登录方法结束==========");
        return result;
    }
}
