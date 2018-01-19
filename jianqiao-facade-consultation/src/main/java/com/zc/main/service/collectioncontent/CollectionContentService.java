package com.zc.main.service.collectioncontent;

import com.zc.common.core.result.Result;
import com.zc.main.entity.collectionconsultation.CollectionConsultation;
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

    /**
     * @description 接口说明 根据资讯id查询收藏记录
     * @author 王鑫涛
     * @date 9:24 2018/1/18
     * @version 版本号
     * @param consulationId 资讯id
     * @return
     */
    CollectionConsultation findOne(Long consulationId);

    /**
     * @description 接口说明 修改收藏资讯状态
     * @author 王鑫涛
     * @date 9:33 2018/1/18
     * @version 版本号
     * @param collectionConsultation 收藏资讯
     * @return
     */
    int updateById(CollectionConsultation collectionConsultation);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create:  2018/1/19 15:08
     * @desc: 收藏内容
     * @version 1.0.0
     * @param member
     * @param consultationId
     * @return
     */
    Result collectionContent(Member member, Long consultationId);
}
