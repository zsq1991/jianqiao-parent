package com.zc.mybatis.dao;

import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.member.Member;

import java.util.Map;

/**
 * @Description :
 * @Created by : tenghui
 * @Creation Date ：2018年01月15日17:25
 */
@MyBatisRepository
public interface MemberMapper extends BasicMapper<Member> {
	/**
	 * @description 接口说明 获得用户信息通过id和uuid
	 * @author 王鑫涛
	 * @date 14:21 2018/1/16
	 * @version 版本号
	 * @param params
	 * @return
	 */
	Map<String,Object> getMemberByIdAndUuid(Map<String, Object> params);
}
