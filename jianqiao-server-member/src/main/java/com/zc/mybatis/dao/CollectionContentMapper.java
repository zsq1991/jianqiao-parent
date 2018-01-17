package com.zc.mybatis.dao;

import com.zc.common.core.orm.mybatis.MyBatisRepository;

import java.util.List;
import java.util.Map;

/**
 * @version ： 1.0.0
 * @package : com.zc.mybatis.dao
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : wangxueyang[wxueyanghj@163.com]
 * @Creation Date ：2018年01月17日13:36
 */
@MyBatisRepository
public interface CollectionContentMapper {
    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param param member_id
     * @return
     * @create: 2018/1/17 13:38
     * @desc: 得到当前用户的收藏
     * @version 1.0.0
     */
    List<Map<String, Object>> getMyCollentent(Map<String, Object> param);
}
