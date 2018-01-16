package com.zc.mybatis.dao;


import com.zc.common.core.orm.mybatis.MyBatisRepository;

import java.util.List;
import java.util.Map;

/**
 * @author xwolf
 * @date 2017-06-19 12:03
 * @since 1.8
 */
@MyBatisRepository
public interface MemberAttachmentMapper {
    /**
     * @description 接口说明  根据id获取用户附件
     * @author 王鑫涛
     * @date 14:27 2018/1/16
     * @version 版本号
     * @param id  资讯id
     * @return
     */
    List<Map<String,Object>> getMemberAttachment(Long id);

}
