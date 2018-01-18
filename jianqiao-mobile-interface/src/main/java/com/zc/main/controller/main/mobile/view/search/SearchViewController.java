package com.zc.main.controller.main.mobile.view.search;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.result.Result;
import com.zc.main.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0.0
 * @description ：历史搜索和热门搜索(未登录)
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/18 11:57
 */
@RestController
@RequestMapping("mobile/view/search")
public class SearchViewController {

    @DubboConsumer(version = "1.0.0", timeout = 30000)
    private SearchService searchService;

    /**
     * @description ：获取所有关键词
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/18 12:03
     * @version 1.0.0
     * @param page 页码
     * @param size 每页大小
     * @return
     */
    @RequestMapping(value = "getKeys", method = RequestMethod.POST)
    public Result getkeys(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          @RequestParam(value = "size", required = false, defaultValue = "6") int size) {
        return searchService.getSearchKeys(null, page, size);
    }
}
