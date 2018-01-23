package com.zc.common.core.cache.redis;

import com.zc.common.core.annotation.RedisCacheEvict;
import com.zc.common.core.annotation.RedisCachePut;
import com.zc.common.core.annotation.RedisCacheable;
import com.zc.common.core.annotation.RedisCaching;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.OxmSerializer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 缓存aop的切面
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月13日 下午2:02:01
 * 
 */
@Aspect
public class RedisCacheAspect {

	private static Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);

	private static ThreadLocal<Boolean> iscache = new ThreadLocal<Boolean>();
	// 字符串缓存工具类
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@SuppressWarnings("unused")
	@Autowired
	private OxmSerializer oxmSerializer;

	@Around("@annotation(com.org.alqframework.annotation.RedisCacheable)")
	public Object cache(ProceedingJoinPoint pjp) {
		if (iscache.get() != null && iscache.get().booleanValue()) {
			try {
				return pjp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		Object result = null;
		try {
			// 获取注解的方法
			Method method = getMethod(pjp);
			// 获取注解类
			RedisCacheable cacheble = method.getAnnotation(RedisCacheable.class);
			// 获取field的值
			String fieldKey = parseKey(cacheble.fieldKey(), method, pjp.getArgs());
			// 判断缓冲区是否有值
			result = redisTemplate.opsForHash().get(cacheble.key(), fieldKey);
			if (result == null) {
				// 运行方法
				result = pjp.proceed();
				redisTemplate.opsForHash().put(cacheble.key(), fieldKey, result);
				if (cacheble.expiretime() != 0) {
					redisTemplate.expire(cacheble.key(), cacheble.expiretime(), TimeUnit.SECONDS);
				}
			}
		} catch (Throwable e) {
			logger.info("redis没有开启，系统缓存功能无法使用:" + e.getMessage());
			try {
				// 运行方法
				result = pjp.proceed();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}

	@Around("@annotation( com.org.alqframework.annotation.RedisCachePut)")
	public Object cachePut(ProceedingJoinPoint pjp) {
		Object result = null;
		try {
			// 获取注解的方法
			Method method = getMethod(pjp);
			// 获取注解类
			RedisCacheable cacheble = method.getAnnotation(RedisCacheable.class);
			// 获取field的值
			String fieldKey = parseKey(cacheble.fieldKey(), method, pjp.getArgs());
			// 运行方法
			result = pjp.proceed();
			redisTemplate.opsForHash().put(cacheble.key(), fieldKey, result);
			if (cacheble.expiretime() != 0) {
				redisTemplate.expire(cacheble.key(), cacheble.expiretime(), TimeUnit.SECONDS);
			}
		} catch (Throwable e) {
			logger.info("redis没有开启，系统缓存功能无法使用:" + e.getMessage());
			try {
				// 运行方法
				result = pjp.proceed();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}

	@Around("@annotation(com.org.alqframework.annotation.RedisCacheEvict)")
	public Object cacheEvict(ProceedingJoinPoint pjp) {
		Object result = null;
		try {
			// 运行方法
			result = pjp.proceed();
			// 获取注解的方法
			Method method = getMethod(pjp);
			// 获取注解类
			RedisCacheEvict redisCacheEvict = method.getAnnotation(RedisCacheEvict.class);
			if (redisCacheEvict.allEntries()) {
				// 删除key值
				redisTemplate.delete(redisCacheEvict.key());
				return result;
			} else {
				// 获取field的值
				String fieldKey = parseKey(redisCacheEvict.fieldKey(), method, pjp.getArgs());
				redisTemplate.opsForHash().delete(redisCacheEvict.key(), fieldKey);
				return result;
			}
		} catch (Throwable e) {
			logger.info("redis没有开启，系统缓存功能无法使用:" + e.getMessage());
			try {
				// 运行方法
				result = pjp.proceed();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}

	@Around("@annotation( com.org.alqframework.annotation.RedisCaching)")
	public Object caching(ProceedingJoinPoint pjp) {
		Object result = null;
		// 获取注解的方法
		Method method = getMethod(pjp);
		// 获取注解类
		RedisCaching redisCaching = method.getAnnotation(RedisCaching.class);
		RedisCacheable[] redisCacheables = redisCaching.cacheable();
		RedisCacheEvict[] redisCacheEvicts = redisCaching.evict();
		RedisCachePut[] redisCachePuts = redisCaching.cachePut();
		try {
			if (iscache.get() == null || !iscache.get().booleanValue()) {
				// 查询缓存的操作
				for (RedisCacheable redisCacheable : redisCacheables) {
					// 获取field的值
					String fieldKey = parseKey(redisCacheable.fieldKey(), method, pjp.getArgs());
					// 判断缓冲区是否有值
					result = redisTemplate.opsForHash().get(redisCacheable.key(), fieldKey);
					if (result == null) {
						// 运行方法
						result = pjp.proceed();
						redisTemplate.opsForHash().put(redisCacheable.key(), fieldKey, result);
						if (redisCacheable.expiretime() != 0) {
							redisTemplate.expire(redisCacheable.key(), redisCacheable.expiretime(),
									TimeUnit.SECONDS);
						}
						break;
					}
				}
			}
			// 删除缓存的操作
			if (result == null) {
				// 运行方法
				result = pjp.proceed();
			}
			for (RedisCacheEvict redisCacheEvict : redisCacheEvicts) {
				if (redisCacheEvict.allEntries()) {
					// 删除key值
					redisTemplate.delete(redisCacheEvict.key());
				} else {
					// 获取field的值
					String fieldKey = parseKey(redisCacheEvict.fieldKey(), method, pjp.getArgs());
					redisTemplate.opsForHash().delete(redisCacheEvict.key(), fieldKey);
				}
			}
			// 修改缓存
			for (RedisCachePut redisCachePut : redisCachePuts) {
				// 获取field的值
				String fieldKey = parseKey(redisCachePut.fieldKey(), method, pjp.getArgs());
				// 运行方法
				result = pjp.proceed();
				redisTemplate.opsForHash().put(redisCachePut.key(), fieldKey, result);
				if (redisCachePut.expiretime() != 0) {
					redisTemplate.expire(redisCachePut.key(), redisCachePut.expiretime(),
							TimeUnit.SECONDS);
				}
			}
		} catch (Throwable e) {
			logger.info("redis没有开启，系统缓存功能无法使用:" + e.getMessage());
			try {
				// 运行方法
				result = pjp.proceed();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
		return result;

	}

	@Around("@annotation(com.org.alqframework.annotation.NoReadCache)")
	public Object noCache(ProceedingJoinPoint pjp) throws Throwable {
		iscache.set(true);
		Object object = null;
		object = pjp.proceed();
		iscache.set(false);
		return object;
	}

	public static void remove(){
		iscache.remove();
	}

	/**
	 * * 获取被拦截方法对象 * * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象 *
	 * 而缓存的注解在实现类的方法上 * 所以应该使用反射获取当前对象的方法对象
	 */
	private Method getMethod(ProceedingJoinPoint pjp) {
		// 获取参数的类型
		Object[] args = pjp.getArgs();
		Class<?>[] argTypes = new Class[pjp.getArgs().length];
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		Method method = null;
		try {
			method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return method;
	}

	/**
	 * * 获取缓存的key * key 定义在注解上，支持SPEL表达式 * @param pjp *
	 * 
	 * @return
	 */
	private String parseKey(String key, Method method, Object[] args) {
		// 获取被拦截方法参数名列表(使用Spring支持类库)
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paraNameArr = u.getParameterNames(method);
		// 使用SPEL进行key的解析
		ExpressionParser parser = new SpelExpressionParser();
		// SPEL上下文
		StandardEvaluationContext context = new StandardEvaluationContext();
		// 把方法参数放入SPEL上下文中
		for (int i = 0; i < paraNameArr.length; i++) {
			context.setVariable(paraNameArr[i], args[i]);
		}
		return parser.parseExpression(key).getValue(context, String.class);
	}
}
