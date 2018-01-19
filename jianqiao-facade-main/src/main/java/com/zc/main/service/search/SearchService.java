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

    /**
     * @description: 查看全部历史关键词列表(已登录)
     * @author:  ZhaoJunBiao
     * @date:  2018/1/18 18:00
     * @version: 1.0.0
     * @param member
     * @param page
     * @param size
     * @return
     */
    Result getHistoryKeys(Member member, int page, int size);

    /**
     * @description: 清空历史关键词
     * @author:  ZhaoJunBiao
     * @date:  2018/1/18 18:00
     * @version: 1.0.0
     * @param member
     * @return
     */
    Result clearKeys(Member member);
}
