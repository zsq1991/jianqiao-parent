package com.zc.common.core.aspect;

import com.zc.common.core.config.RedisConfig;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.common.core.utils.JedisUtils;
import com.zc.common.core.utils.MD5Util;
import com.zc.common.core.utils.SignUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description 防爆拦截器
 * @author whl
 * @date 2018-01-16 13:38
 * @version 1.0.0
 */
@Component  
@Aspect
@Order
public class AdviceExplosionproof {

	private static Logger logger = LoggerFactory
			.getLogger(AdviceExplosionproof.class);
	@Autowired
	private RedisConfig redisConfig;

	/**
	 * @description 防爆拦截器
	 * @author whl
	 * @date 2018-01-16 13:38
	 * @version 1.0.0
	 * @param point 切入点
	 * @return
	 * @throws Throwable
	 */
	// 方法执行的前后调用  
	@Around("@annotation(com.zc.common.core.annotation.Explosionproof )")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info(Thread.currentThread().getName()+"防爆线程名称------------------------");
		JedisUtils jedisUtils = JedisUtils.getRu(redisConfig.getHost(), redisConfig.getPort());
		Result result = new Result();

		//获取请求路径
		String requestURI = request.getRequestURI();
		logger.info("请求路径:"+requestURI);
		Map<String,String[]> map =request.getParameterMap();
		String signData = SignUtils.mapToLinkString2(map);
		String sign = "";
		byte[] b = null;
		try {
			b = (MD5Util.getMD5Encode(signData,"utf-8")+"TY"+requestURI).getBytes("utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(signData+":签名加密异常");
		}
		if (b != null) {
			sign = Base64Utils.encodeToString(b);
		}
		logger.info("sign="+sign+"****************************");
		Boolean num = jedisUtils.exists(sign);
		if(!num ){
			//设置失效时间
			String setex = jedisUtils.setex(sign, sign, 10);
			logger.info("方法:"+point.getSignature().getName()+"防爆通过，准备添加redis");
			//成功返回
			String successStr="OK";
			if (successStr.equals(setex)){
				logger.info("方法:"+point.getSignature().getName()+"添加redis成功，准备执行");
				Object object = point.proceed();
				Class<? extends Object> class1 = object.getClass();
				Class<? extends Result> class2 = result.getClass();
				if(class1 != class2){
					return object;
				}
				result = (Result) object;
				if(result.getCode().intValue() != 1){
					logger.info("方法:"+point.getSignature().getName()+"执行，返回失败，准备删除redis");
					Long hdel = jedisUtils.del(sign);
					if(hdel.longValue() > 0L){
						logger.info("方法:"+point.getSignature().getName()+"删除redis成功");
					}else{
						logger.info("方法:"+point.getSignature().getName()+"删除redis失败");
					}
				}
				return result;
			}else{
				logger.info("方法:"+point.getSignature().getName()+"添加redis失败，拦截");
				return ResultUtils.returnError("提交异常,请联系管理员");
			}
		}else{
			logger.info("方法:"+point.getSignature().getName()+"防爆处理，拦截");
			return ResultUtils.returnError("请勿重复提交");
		}
	}
}
