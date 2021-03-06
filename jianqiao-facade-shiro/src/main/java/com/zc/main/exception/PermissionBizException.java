package com.zc.main.exception;

import com.zc.common.config.springmvcexception.BizException;
import com.zc.common.core.shiro.PermissionEnum;

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
public class PermissionBizException extends BizException{

	private static final long serialVersionUID = 902198845675620978L;

	public PermissionBizException(PermissionEnum exceptionEnum) {
		super(exceptionEnum.getMsg(),exceptionEnum.getCode());
	}

	public PermissionBizException(String msg, String code) {
		super(msg, code);
	}

	public PermissionBizException(String msg) {
		super(msg);
	}

	public PermissionBizException() {
	}

}
