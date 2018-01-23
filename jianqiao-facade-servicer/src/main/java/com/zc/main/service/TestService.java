/**
 * Project Name:cc-server-main
 * File Name:TestService.java
 * Package Name:com.zc.service
 * Date:2017年8月24日上午10:32:55
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.zc.main.service;

import com.zc.main.entity.TestTable;

/**
 * ClassName:TestService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年8月24日 上午10:32:55 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public interface TestService {

	/**
	 * 保存和更新测试表
	 * @param t
	 * @return
	 */
	boolean saveAndModify(TestTable t);
	
	/**
	 * 查询测试表
	 * @param id
	 * @return
	 */
	TestTable get(Long id);
	
}
