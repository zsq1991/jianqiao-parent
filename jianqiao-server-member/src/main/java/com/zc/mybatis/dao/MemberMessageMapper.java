package com.zc.mybatis.dao;

import com.zc.common.core.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Created by : zhaoshuaiqi
 * @Creation Date ：2018/1/17
 */
@MyBatisRepository
public interface MemberMessageMapper {

    /**
     * * @author:  zhaoshuaiqi
     * @create:  2018/1/17
     * @desc: 用户个人信息获取【已关注或未关注】
     * @version 1.0.0
     * @param map
     * @return
     */
    List<Map<String, Object>> getMemberInfo(Map<String, Object> map);

    /**
     * @author:  zhaoshuaiqi
     * @create:  2018/1/17
     * @desc: 获取会员信息
     * @version 1.0.0
     * @param memberId
     * @return
     */
    List<Map<String, Object>> getMemberMessageList(Long memberId);
    //检验昵称是否已存在
    List<Map<String, Object>> getMemberByNickname(String nickname);

    /**
     * @author:  zhaoshuaiqi
     * @create:  2018/1/17
     * @desc: 根据id修改头像
     * @param attachmentId
     * @param id
     */
    void updateMemberLogoById(@Param("attachmentId") Long attachmentId, @Param("id") Long id);

    /**
     * @author:  zhaoshuaiqi
     * @create:  2018/1/17
     * @desc: 获根据id修改昵称
     * @param nickname
     * @param id
     */
    void updateMemberNickNameById(@Param("nickname") String nickname, @Param("id") Long id);

    /**
     * @author:  zhaoshuaiqi
     * @create:  2018/1/17
     * @desc: 获根据id修改性别
     * @param sexId
     * @param id
     */
    void updateMemberSexById(@Param("sexId") Integer sexId, @Param("id") Long id);


}
