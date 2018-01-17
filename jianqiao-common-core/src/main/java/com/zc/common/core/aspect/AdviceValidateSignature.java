package com.zc.common.core.aspect;

import com.alibaba.fastjson.JSON;
import com.zc.common.core.result.Result;
import com.zc.common.core.utils.MD5Util;
import com.zc.common.core.utils.SignUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author whl
 * @version 1.0.0
 * @description 验签拦截
 * @date 2018-01-17 11:12
 */
@Component
@Aspect
@Order
public class AdviceValidateSignature {

    private static Logger logger = LoggerFactory
            .getLogger(AdviceValidateSignature.class);

    /**
     * 白名单
     */
    private final static String[] PUBLIC_URLS = {
            "/mobile/view/attachment/upload",//附件上传
            "/mobile/view/message/sendMsg",//认证发短信
            "/mobile/view/securitycode/registersend",//注册发送短信
            "/mobile/view/securitycode/loginsend",//登录发送短信
            "/mobile/view/picture/url", //健桥首页
            "/mobile/view/app/share", //分享
            "/mobile/view/app/download",//下载APP二维码.
            "/mobile/view/picture/url"//合作相关
    };
    /**
     * @param point 切入点
     * @return
     * @throws Throwable
     * @description 验签拦截
     * @author whl
     * @date 2018-01-17 11:26
     * @version 1.0.0
     */
    @Around("execution(* com.zc.main.controller.main.mobile.*.*.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        Result result = new Result();
        String objSign = request.getParameter("sign");
        String objType = request.getParameter("client_type");
        String objTimestamp = request.getParameter("timestamp");
        //获取请求路径
        String requestURI = request.getRequestURI();
        logger.info("========验签开始,sign={},client_type={},timestamp={},uri={}", objSign,objType,objTimestamp,requestURI);
        for (String url : PUBLIC_URLS) {
            if (url.equals(requestURI)) {
                return execute(point);
            }
        }
        if (objType == null) {
            result.setCode(201);
            result.setMsg("客户端为空");
            logger.info("========验签异常,result={}", JSON.toJSONString(result));
            return result;
        }
        if ("W".equals(objType)) {
            logger.info("========微信客户端不验签,sign={},client_type={},timestamp={},uri={}", objSign,objType,objTimestamp,requestURI);
            return execute(point);
        }
        if (objTimestamp == null) {
            result.setCode(202);
            result.setMsg("时间戳为空");
            logger.info("========验签异常,result={}", JSON.toJSONString(result));
            return result;
        }
        if (objSign == null) {
            result.setCode(205);
            result.setMsg("签名为空");
            logger.info("========验签异常,result={}", JSON.toJSONString(result));
            return result;
        }
        if (!"I".equals(objType) && !"A".equals(objType)) {
            result.setCode(203);
            result.setMsg("客户端异常");
            logger.info("========验签异常,result={}", JSON.toJSONString(result));
            return result;
        }
        Map<String, String[]> map = request.getParameterMap();
        String signData = SignUtils.mapToLinkString2(map);
        signData = StringEscapeUtils.unescapeXml(signData);
        String sign = "";
        byte[] b = null;
        try {
            b = (MD5Util.MD5Encode(signData, "utf-8") + "JQ" + objType.toString()).getBytes("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(signData + ":签名加密异常");
        }
        if (b != null) {
            sign = Base64Utils.encodeToString(b);
        }
        logger.info("========签名加密完成,sign={}", sign);
        logger.info("========签名比较,objSign={},sign={}", objSign, sign);
        String signApp = objSign.toString();
        if ((signApp.trim()).equals(sign.trim())) {
            result.setCode(0);
            result.setMsg("验签通过");
            logger.info("========验签通过,result={}", JSON.toJSONString(result));
            return execute(point);
        } else {
            result.setCode(204);
            result.setMsg("验签失败");
            logger.info("========验签失败,result={}", JSON.toJSONString(result));
            return result;
        }
    }

    /**
     * @description 执行方法
     * @author whl
     * @date 2018-01-17 11:41
     * @version 1.0.0
     * @param point 切入点
     * @return
     * @throws Throwable
     */
    private Object execute(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String objSign = request.getParameter("sign");
        String objType = request.getParameter("client_type");
        String objTimestamp = request.getParameter("timestamp");
        //获取请求路径
        String requestURI = request.getRequestURI();
        logger.info("========验签结束,执行方法,sign={},client_type={},timestamp={},uri={}", objSign,objType,objTimestamp,requestURI);
        Object object = point.proceed();
        return object;
    }
}