package com.zc.exception;

import com.common.util.exception.BusinessException;
import com.zc.common.core.shiro.Result;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述：权限模块异常处理
 *
 * @author ： Mr.chang
 *
 * @创建时间：2017年3月14日
 *
 * @Copyright @2017 by Mr.chang
 */
public class ServicerException extends BusinessException {

	private static final long serialVersionUID = 902198845675620978L;

	public ServicerException(Result result) {
        super(result);
	}



}
