package com.zc.mybatis.dao;


import com.zc.main.entity.membermsg.MemberMsg;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年6月18日 下午5:38:01
 * 
 */
public interface MemberMsgMapper extends BaseMapper<MemberMsg> {

    void deleteMemberMsgByConsulatationId(@Param("memberId") Long memberId, @Param("types") Integer types, @Param("id") Long id);
}
