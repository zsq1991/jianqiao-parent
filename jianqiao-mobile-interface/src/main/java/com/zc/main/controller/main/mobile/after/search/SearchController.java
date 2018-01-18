package com.zc.main.controller.main.mobile.after.search;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.search.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @package : com.zc.main.controller.main.mobile.after.search
 * @progect : jianqiao-parent
 * @Description :历史搜索和热门搜索
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月18日15:13
 */
@Controller
@RequestMapping("mobile/after/search")
public class SearchController {

    private static Logger logger = LoggerFactory.getLogger(SearchController.class);

    @DubboConsumer(version = "1.0.0",timeout = 30000)
    private SearchService searchService;

    /**
     * @description: 查看全部历史关键词列表(已登录)
     * @author:  ZhaoJunBiao
     * @date:  2018/1/18 15:23
     * @version: 1.0.0
     * @param member
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "getHistoryKeys",method = RequestMethod.POST)
    public Result getHistoryKeys(@MemberAnno Member member,
                                 @RequestParam(value = "page",defaultValue = "1",required = false)int page,
                                 @RequestParam(value = "size",defaultValue = "6",required = false)int size){
        return searchService.getHistoryKeys(member,page,size);
    }

    /**
     * @description: 清空历史关键词
     * @author:  ZhaoJunBiao
     * @date:  2018/1/18 15:33
     * @version: 1.0.0
     * @param member
     * @return
     */
    @RequestMapping(value = "clear",method = RequestMethod.POST)
    public Result  clearKeys(@MemberAnno Member member){
        return searchService.clearKeys(member);
    }
}
