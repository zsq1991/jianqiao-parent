package com.zc.common.core.excel.poi;

import org.apache.poi.ss.usermodel.Row;

/**
 * 第一行数据获取的回调函数
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-8 上午10:34:54
 * 
 */
public interface ExcelFirstCallBack {
	public boolean paseFirstSheet(final Row row); 
}
