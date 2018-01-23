package com.zc.main.exception;

import com.common.util.exception.businessException;
import com.zc.common.core.shiro.Result;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述：权限模块异常处理
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月14日
 *
 * @Copyright @2017 by Mr.chang
 */
public class MainException extends businessException {

	private static final long serialVersionUID = 902198845675620978L;

	public MainException(Result result) {
        super(result);
	}
}
