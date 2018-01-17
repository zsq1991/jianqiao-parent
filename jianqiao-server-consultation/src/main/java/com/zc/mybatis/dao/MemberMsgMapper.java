package com.zc.mybatis.dao;


import com.zc.main.entity.membermsg.MemberMsg;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author wudi
 * @version v1.0
 * @Description: TODO
 * @create-time 2017年6月18日 下午5:38:01
 */
public interface MemberMsgMapper extends BaseMapper<MemberMsg> {

    void deleteMemberMsgByConsulatationId(@Param("memberId") Long memberId, @Param("types") Integer types, @Param("id") Long id);

    public void deleteMemberMsgBycontentId(@Param("mId") Long mId, @Param("type") Integer type, @Param("commentid") Long commentid);
}
