package com.common.util.mybatis;

import org.apache.ibatis.type.Alias;


/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-2-27 下午4:36:10
 * 
 */
@Alias("myUser")
public class MyUser {
	
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
