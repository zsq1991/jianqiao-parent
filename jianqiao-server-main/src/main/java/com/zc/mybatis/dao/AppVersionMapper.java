package com.zc.mybatis.dao;

import java.util.List;
import java.util.Map;

/**
 * @package : com.zc.mybatis.dao
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月18日16:35
 */
public interface AppVersionMapper {
    /**
     * @description:  查询版本相关信息
     * @author:  ZhaoJunBiao
     * @date:  2018/1/18 16:36
     * @version: 1.0.0
     * @return
     */
    List<Map<String,Object>> getAppVersion();
}
