package com.zc.common.core.excel.poi;

/**
 * excel文件的类型
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-7 下午2:43:50
 * 
 */
public enum ExcelVersion {
	/**
	 * 2003版本的excel
	 */
	xls {
		@Override
		public String getName() {
			return "2003";
		}
	},
	/**
	 * 2007版本的excel
	 */
	xlsx {
		@Override
		public String getName() {
			return "2007";
		}
	};
	public abstract String getName();
}
