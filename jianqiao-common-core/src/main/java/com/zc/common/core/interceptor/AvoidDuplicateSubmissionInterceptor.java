/**
 * Project Name:zte-buyer-pc-interface
 * Package Name:com.alqsoft.interceptor
 * File Name:AvoidDuplicateSubmissionInterceptor.java
 * Create Date:2014-12-3 下午3:31:52
 * SVN:$Id$
 * Copyright © 2014 厦门卓讯信息技术有限公司 All rights reserved.
 */

package com.zc.common.core.interceptor;

        import com.zc.common.core.utils.UniqueUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;


/*
        *
        * 防止重复提交过滤器.
        *
        * @author 张灿
        * @since JDK 1.7
        * @see
*/


public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(AvoidDuplicateSubmissionInterceptor.class);

    //获取到string  redis对象
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
//		session.setAttribute(arg0, arg1);
//		session.removeAttribute(arg0);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
//        String token=(String) redisTemplate.opsForHash().get("tokens", "token"+request.getSession().getId());
        String token=(String)request.getSession().getAttribute("token");
        logger.info("第一步："+Thread.currentThread().getName()+"="+token);
        if(StringUtils.isBlank(token)){
            String uuid = UniqueUtils.getUUID();
            redisTemplate.opsForHash().put("tokens", "token"+request.getSession().getId(), uuid);
            request.getSession().setAttribute("token",uuid);
        }
        AvoidDuplicateSubmission annotation = method.getAnnotation(AvoidDuplicateSubmission.class);
        if (annotation != null) {
            boolean needRemoveSession = annotation.needRemoveToken();
            if (needRemoveSession) {
                if (isRepeatSubmit(request)) {
                    logger.warn("请不要重复提交表单");
                    PrintWriter out = response.getWriter();
                    out.write("{\"code\":\"-1\",\"msg\":\"请勿重复提交表单\"}");
                    out.close();
                    return false;
                }
                redisTemplate.opsForHash().delete("tokens", "token"+request.getSession().getId());
                request.getSession().removeAttribute("token");
            }
            logger.info("第二步："+Thread.currentThread().getName()+"="+(String) redisTemplate.opsForHash().get("tokens", "token"+request.getSession().getId()));
        }
        return true;
    }

    // 生成一个唯一值的token
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) redisTemplate.opsForHash().get("tokens", "token"+request.getSession().getId());;

        String clinetToken = request.getParameter("token");
        logger.info("进入对比的到数值："+serverToken+"="+clinetToken);
        if (StringUtils.isBlank(serverToken)) {
            return true;
        }
        if (StringUtils.isBlank(clinetToken)) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
}
