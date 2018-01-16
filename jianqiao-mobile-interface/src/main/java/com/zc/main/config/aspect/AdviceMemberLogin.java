package com.zc.main.config.aspect;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.member.MemberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 登录拦截器
 * @author whl
 * @date 2018-01-16 12:44
 * @version 1.0.0
 */
@Component
@Aspect
@Order
public class AdviceMemberLogin {  
	
	private static Log logger = LogFactory
			.getLog(AdviceMemberLogin.class);

	@Reference(version = "1.0.0")
	private MemberService memberService;

    /**
     * @description 登录拦截
	 * @author whl
	 * @date 2018-01-16 12:45
	 * @version 1.0.0
	 * @param point 切入点
	 * @return
     * @throws Throwable
	 */
	// 方法执行的前后调用  
    @Around("execution(* com.zc.main.controller.main.mobile.after.*.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    	logger.info(Thread.currentThread().getName()+"登录=========线程名称===========");
    	Result result = new Result();
    	String phone = request.getParameter("phone");
    	String uuid = request.getParameter("uuid");
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", phone);
		params.put("uuid", uuid);
		if(phone==null|| "".equals(phone) ||uuid==null|| "".equals(uuid)){
			 result.setCode(101);
			 result.setMsg("用户未登录");
			 return result;
		}
		Member member = this.memberService.getMemberByPhoneAndUuid(params);
		if(member == null){
			 result.setCode(102);
			 result.setMsg("用户登录异常");
    		 return result;
		}
        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Annotation[][] methodAnnotations = method.getParameterAnnotations();
        int length=methodAnnotations.length;
        if(length>0){
	        for (int i=0;i<length;i++) {
	        	if(methodAnnotations[i].length>0){
		        	Annotation annotation=methodAnnotations[i][0];
		        	//访问目标方法的参数：
		        	Object[] args = point.getArgs();
		        	if(annotation.annotationType().getName().equals(MemberAnno.class.getName()))
		        	{
						Member memberArg=(Member) args[i];
						BeanUtils.copyProperties(member,memberArg);
		        	}
	        	}
			}
        }
        //执行方法
        Object object = point.proceed();
		return object;
    }  
}  