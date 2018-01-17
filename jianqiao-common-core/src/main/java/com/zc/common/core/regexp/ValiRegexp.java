package com.zc.common.core.regexp;

/**
 * 字符串的正则表达式的枚举工具
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-11 上午12:13:31
 * 
 */
public enum ValiRegexp {
	/**
	 * 0到9的数字
	 */
	INTEGER {
		@Override
        public String getName() {
			return "^[0-9]+$";
		}
	},
	/**
	 * 正整数
	 */
	PINTEGER {
		@Override
		public String getName() {
			return "^[0-9]*[1-9][0-9]*$";
		}
	},
	/**
	 * 负整数
	 */
	NINTEGER {
		@Override
		public String getName() {
			return "^-[0-9]*[1-9][0-9]*$";
		}
	},
	/**
	 * 整数
	 */
	PNINTEGER {
		@Override
		public String getName() {
			return "^-?\\d+$";
		}
	},
	/**
	 * 小数
	 */
	DECIMALS {
		@Override
		public String getName() {
			return "^(-?\\d+)(\\.\\d+)?$";
		}
	},
	/**
	 * 中国大陆身份证号(15位或18位)
	 */
	IDCARD {
		@Override
		public String getName() {
			return "^\\d{15}(\\d\\d[0-9xX])?$";
		}
	},
	/**
	 * 中国大陆邮政编码
	 */
	POSTCODE {
		@Override
		public String getName() {
			return "^[1-9]\\d{5}$";
		}
	},
	/**
	 * 中国大陆手机号码
	 */
	MOBILE {
		@Override
		public String getName() {
			return "^1\\d{10}$";
		}
	},
	/**
	 * 中国大陆固定电话号码
	 */
	PHONE {
		@Override
		public String getName() {
			return "^(\\d{4}-|\\d{3}-)?(\\d{8}|\\d{7})$";
		}
	},
	/**
	 * 中文及全角标点符号(字符)
	 */
	CHINAMARK {
		@Override
		public String getName() {
			return "^[\\u3000-\\u301e\\ufe10-\\ufe19\\ufe30-\\ufe44\\ufe50-\\ufe6b\\uff01-\\uffee]+$";
		}
	},
	/**
	 * 汉字(字符)
	 */
	CHINESE {
		@Override
		public String getName() {
			return "^[\u4e00-\u9fa5]+$";
		}
	},
	/**
	 * 时间(小时:分钟, 24小时制)
	 */
	TIME {
		@Override
		public String getName() {
			return "^((1|0?)[0-9]|2[0-3]):([0-5][0-9])$";
		}
	},
	/**
	 * 日期(月/日/年)
	 */
	DATEONE {
		@Override
		public String getName() {
			return "^((0?[1-9]{1})|(1[1|2]))/(0?[1-9]|([12][1-9])|(3[0|1]))/(\\d{4}|\\d{2})$";
		}
	},
	/**
	 * 日期(年-月-日)
	 */
	DATETWO {
		@Override
		public String getName() {
			return "^(\\d{4}|\\d{2})-((0?([1-9]))|(1[1|2]))-((0?[1-9])|([12]([1-9]))|(3[0|1]))$";
		}
	},
	/**
	 * 密码(由数字/大写字母/小写字母/标点符号组成，四种都必有，8位以上)
	 */
	PASSWORD {
		@Override
		public String getName() {
			return "^(?=^.{8,}$)(?=.*\\d)(?=.*\\W+)(?=.*[A-Z])(?=.*[a-z])(?!.*\n).*$";
		}
	},
	/**
	 * 电子邮件(Email)
	 */
	EMAIL {
		@Override
		public String getName() {
			return "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		}
	},
	/**
	 * IP地址(IP Address)
	 */
	IP {
		@Override
		public String getName() {
			return "^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$";
		}
	},
	/**
	 * 网址（URL）
	 */
	URL {
		@Override
		public String getName() {
			return "^[a-zA-z]+://[^\\s]*$";
		}
	},
	/**
	 * QQ号码
	 */
	QQ {
		@Override
		public String getName() {
			return "^[1-9]\\d{4,}$";
		}
	},
	/**
	 * 匹配由数字和26个英文字母组成的字符串
	 */
	SHUZIENG {
		@Override
		public String getName() {
			return "^[A-Za-z0-9]+$";
		}
	};
	public abstract String getName();
}
