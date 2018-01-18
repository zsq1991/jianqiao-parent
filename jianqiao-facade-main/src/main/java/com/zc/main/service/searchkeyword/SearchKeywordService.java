package com.zc.main.service.searchkeyword;

import java.util.List;
import java.util.Map;

/**
 * @description ：热门搜索
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/18 13:33
 * @version 1.0.0
 */
public interface SearchKeywordService {

    /**
     * @description ： 获取热门词
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/18 13:33
     * @version 1.0.0
     */
    List<Map<String,Object>> getKeys();
}