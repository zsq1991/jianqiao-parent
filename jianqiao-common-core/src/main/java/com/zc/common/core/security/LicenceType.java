package com.zc.common.core.security;

/**
 * 证书类型
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-23 下午12:59:49
 * 
 */
public enum LicenceType {
	/**
	 * 获取.cer扩展名的证书
	 */
	X_509 {
		@Override
		public String getName() {
			return "X.509";
		}
	},
	/**
	 * 加载证书库
	 */
	JKS {
		@Override
		public String getName() {
			return "JKS";
		}
	},
	/**
	 * .p12扩展名的证书
	 */
	PKCS12 {
		@Override
		public String getName() {
			return "PKCS12";
		}
	};

	public abstract String getName();
}
