package com.zc.common.core.basemapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author by Moya on 2017/11/27.
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
