package com.zc.common.core.orm;


/**
 * service基本操作类型
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-6-6 下午10:45:12
 * 
 */
public interface BaseService<T> {
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public T get(Long id);
}
