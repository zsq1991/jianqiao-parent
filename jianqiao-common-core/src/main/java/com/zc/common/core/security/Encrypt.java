package com.zc.common.core.security;

/**
 * 加密种类
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-22 下午4:27:24
 * 
 */
public enum Encrypt {
	/**
	 * MD5加密
	 */
	MD5 {
		@Override
		public String getName() {
			return "MD5";
		}
	},
	/**
	 * SHA-1加密
	 */
	SHA_1 {
		@Override
		public String getName() {
			return "SHA-1";
		}
	},
	/**
	 * 对称算法：DES（实际密钥只用到56 位）
	 */
	DES {
		@Override
		public String getName() {
			return "DES";
		}
	},
	/**
	 * 对称算法:AES（支持三种密钥长度：128、192、256位），通常首先128位
	 */
	AES {
		@Override
		public String getName() {
			return "AES";
		}
	},
	/**
	 * RSA算法是第一个能同时用于加密和数字签名的算法，也易于理解和操作。
	 * RSA是被研究得最广泛的公钥算法，从提出到现在已近二十年，经历了各种攻击的考验，逐渐为人们接受，普遍认为是目前最优秀的公钥方案之一
	 */
	RSA {
		@Override
		public String getName() {
			return "RSA";
		}
	};
	public abstract String getName();
}
