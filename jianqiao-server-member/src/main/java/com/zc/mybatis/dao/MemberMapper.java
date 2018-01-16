package com.zc.mybatis.dao;

import com.common.util.mybatis.BasicMapper;
import com.zc.main.entity.member.Member;

import java.util.Map;

/**
 * @Description :
 * @Created by : tenghui
 * @Creation Date ：2018年01月15日17:25
 */
public interface MemberMapper extends BasicMapper<Member> {
    /**
     * 根据手机号和UUID查询用户
     * @description 根据手机号和UUID查询用户
     * @author whl
     * @date 2018-01-16 12:39
     * @version 1.0.0
     * @param params 包含id和uuid的Map
     * @return
     */
    Member getMemberByPhoneAndUuid(Map<String, Object> params);
}
