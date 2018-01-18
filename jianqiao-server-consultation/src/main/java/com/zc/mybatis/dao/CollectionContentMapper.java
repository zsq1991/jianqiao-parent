package com.zc.mybatis.dao;

import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.collectionconsultation.CollectionConsultation;

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
public interface CollectionContentMapper extends BasicMapper<CollectionConsultation>{
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
    /**
     * @description 接口说明 根据资讯id查询收藏记录
     * @author 王鑫涛
     * @date 9:24 2018/1/18
     * @version 版本号
     * @param consulationId 资讯id
     * @return
     */
    CollectionConsultation findOne(Long consulationId);
    
}
