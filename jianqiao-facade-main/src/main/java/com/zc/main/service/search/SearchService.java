package com.zc.main.service.search;


import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;

/**
 * @version 1.0.0
 * @description ：历史搜索和热门搜索
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/18 11:59
 */
public interface SearchService {

    /**
     * @description ：获取所有关键词
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/18 12:03
     * @version 1.0.0
     * @param page 页码
     * @param size 每页大小
     * @return
     */
    Result getSearchKeys(Member member, int page, int size);

    Result getHistoryKeys(Member member, int page, int size);

    /*Result clearKeys(Member member);*/
}
