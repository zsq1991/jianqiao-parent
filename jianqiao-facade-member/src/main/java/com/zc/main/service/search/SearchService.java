package com.zc.main.service.search;

import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;

/**
 * @package : com.zc.main.service.search
 * @progect : jianqiao-parent
 * @Description :历史搜索和热门搜索
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月18日15:16
 */
public interface SearchService {

    Result getHistoryKeys(Member member, Integer page, Integer size);
}
