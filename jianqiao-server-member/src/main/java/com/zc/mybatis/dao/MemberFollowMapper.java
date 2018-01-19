package com.zc.mybatis.dao;

import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberfollow.MemberFollow;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Map;

/**
 * @version ： 1.0.0
 * @package : com.zc.mybatis.dao
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : wangxueyang[wxueyanghj@163.com]
 * @Creation Date ：2018年01月17日9:45
 */
@MyBatisRepository
public interface MemberFollowMapper {

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param mId   登录着的id
     * @param pages 页码
     * @param sizes 页大小
     * @return
     * @create: 2018/1/17 10:09
     * @desc: 获取被登录者关注的id集合
     * @version 1.0.0
     */
    List<Map> getFocusMIdList(@Param("mId") Long mId, @Param("pages") int pages, @Param("sizes") int sizes);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param id  被关注者的id
     * @param mId 关注者id
     * @return
     * @create: 2018/1/17 10:16
     * @desc: 判断是相互关注还是单向关注
     * @version 1.0.0
     */
    MemberFollow getFocusById(@Param("id") Long id, @Param("mId") Long mId);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param id 用户的id
     * @return
     * @create: 2018/1/17 10:17
     * @desc: 通过id获取昵称和头像地址
     * @version 1.0.0
     */
    Map getFanList(Long id);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param id 资讯id
     * @return
     * @create: 2018/1/17 10:19
     * @desc: 通过用户id获取资讯标题
     * @version 1.0.0
     */
    Map getConsulatationTitleByMId(Long id);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param mId   登录者的id
     * @param pages 页码
     * @param sizes 页大小
     * @return
     * @create: 2018/1/17 11:14
     * @desc: 获取关注登录者的id集合
     * @version 1.0.0
     */
    List<Map> getMIdList(@Param("mId") Long mId, @Param("pages") int pages, @Param("sizes") int sizes);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create:  2018/1/18 17:50
     * @desc: 通过关注者id和被关注者id获取实体类
     * @version 1.0.0
     * @param fId 被关注者id
     * @param mId 关注者id
     * @return
     */
    MemberFollow getMemberFollowByData(@Param("fId")Long fId,@Param("mId") Long mId);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create:  2018/1/18 18:12
     * @desc: 更新会员粉丝数 关注数
     * @version 1.0.0
     * @param member 当前更新的内容
     * @return
     */
    Integer updateNumById(Member member);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create:  2018/1/18 21:21
     * @desc: 获取关注信息
     * @version 1.0.0
     * @param fId 关注者id
     * @param mId 被关注者id
     * @return
     */
    MemberFollow getDataById(@Param("fId")Long fId,@Param("mId")Long mId );

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create:  2018/1/18 21:28
     * @desc: 更新关注信息
     * @version 1.0.0
     * @param memberFollow 关注信息
     * @return
     */
    Integer updateMemberFollowById(MemberFollow memberFollow);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create:  2018/1/18 21:36
     * @desc: 插入关注新纪录
     * @version 1.0.0
     * @param memberFollow
     * @return
     */
    Integer insertMemberFollow(MemberFollow memberFollow);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create:  2018/1/18 21:58
     * @desc: 根据id获取当前关注记录
     * @version 1.0.0
     * @param id
     * @return
     */
    MemberFollow getMemberFollowById(@Param("id") Long id);
}
