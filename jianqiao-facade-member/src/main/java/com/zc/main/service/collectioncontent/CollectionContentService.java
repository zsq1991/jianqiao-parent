package com.zc.main.service.collectioncontent;

import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;

/**
 * @version ： 1.0.0
 * @package : com.zc.main.service.collectioncontent
 * @progect : jianqiao-parent
 * @Description : 收藏业务
 * @Created by : wangxueyang[wxueyanghj@163.com]
 * @Creation Date ：2018年01月17日11:41
 */
public interface CollectionContentService {
    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param member 当前登录者
     * @param page   页码
     * @param rows   页大小
     * @return
     * @create: 2018/1/17 11:50
     * @desc: 收藏列表
     * @version 1.0.0
     */
    Result mycollection(Member member, Integer page, Integer rows);
}
