package com.zc.mybatis.dao;

import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.memberfollow.MemberFollow;
import org.apache.ibatis.annotations.Param;

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

}
