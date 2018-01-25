package com.zc.main.service.memberfollow;

import com.zc.common.core.result.Result;

/**
 * @version ： 1.0.0
 * @package : com.zc.main.service.memberfollow
 * @progect : jianqiao-parent
 * @Description : 用户相关信息  关注  粉丝
 * @author  by : wangxueyang[wxueyanghj@163.com]
 * @Creation Date ：2018年01月16日18:04
 */
public interface MemberFollowService {
    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param fId 关注用户
     * @param mId 关注着的id
     * @return
     * @create: 2018/1/16 18:07
     * @desc: 关注用户
     * @version 1.0.0
     */
    public Result focusMember(Long fId, Long mId);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create:  2018/1/16 18:08
     * @desc:  粉丝列表
     * @version 1.0.0
     * @param mId 登录者的id
     * @param page 页码
     * @param size 页大小
     * @return
     */
    public Result getFansList(Long mId, int page, int size);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create:  2018/1/16 18:09
     * @desc: 关注列表
     * @version 1.0.0
     * @param mId 登录者的id
     * @param page 页码
     * @param size 页大小
     * @return
     */
    public Result getFocusList(Long mId, int page, int size);

}
