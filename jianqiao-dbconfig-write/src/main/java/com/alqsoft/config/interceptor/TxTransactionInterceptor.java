
package com.alqsoft.config.interceptor;

import com.codingapi.tx.dubbo.interceptor.TxManagerInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author  by lorne on 2017/6/28.
 */
@Aspect
@Component
public class TxTransactionInterceptor implements Ordered{

    @Autowired
    private TxManagerInterceptor txManagerInterceptor;


    @Override
    public int getOrder() {
        return 1;
    }

    @Around("execution(* com.zc..impl..*(..))")
    public Object around(ProceedingJoinPoint point)throws Throwable{
        return txManagerInterceptor.around(point);
    }
}

