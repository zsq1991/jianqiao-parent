package com.zc.mybatis.dao;


import com.zc.common.core.orm.mybatis.MyBatisRepository;

import java.util.List;
import java.util.Map;

/**
 * @author xwolf
 * @date 2017-05-15 10:58
 * @since 1.8
 */
@MyBatisRepository
public interface SearchKeywordMapper {

    List<Map<String,Object>> getKeys();
}
