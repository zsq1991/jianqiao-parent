package com.zc.impl.search;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.service.member.MemberService;
import com.zc.main.service.membersearchconsultation.MembersearchconsultationService;
import com.zc.main.service.search.SearchService;
import com.zc.main.service.searchkeyword.SearchKeywordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @version 1.0.0
 * @description ：历史搜索和热门搜索
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/18 12:00
 */
@Component
@Service(version = "1.0.0", interfaceClass = SearchService.class)
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {

    private static Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private SearchKeywordService searchKeywordService;

    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private MemberService memberService;

    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private MembersearchconsultationService membersearchconsultationService;


    /**
     * 获取所有关键词
     *
     * @return
     */
    @Override
    public Result getSearchKeys(Member member, int page, int size) {
        log.info("===================获取所有关键词开始=========，member={}", member);
        Map<String, Object> result = Maps.newHashMap();
        List<Map<String, Object>> searchHistory = Lists.newArrayList();
        result.put("history", searchHistory);
        try {
            if (!Objects.isNull(member)) {
                Map<String, Object> param = Maps.newHashMap();
                param.put("page", (page - 1) * size);
                param.put("size", size);
                param.put("mid", member.getId());
                searchHistory = membersearchconsultationService.findSearchKeywordByMember(param);
                result.put("history", searchHistory);
            }

            List<Map<String, Object>> searchKeys = searchKeywordService.getKeys();
            result.put("hots", searchKeys);
            log.info("==================获取关键词结束===============");
            return ResultUtils.returnSuccess("成功", result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.info("==================获取关键词结束===============");
            return ResultUtils.returnError("接口调用失败");
        }
    }

    /**
     * @description: 查看全部历史关键词列表(已登录)
     * @author:  ZhaoJunBiao
     * @date:  2018/1/18 15:23
     * @version: 1.0.0
     * @param member  用户信息
     * @param page 页码
     * @param size 每页大小
     * @return
     */
    @Override
    public Result getHistoryKeys(Member member, int page, int size) {
        log.info("==================获取历史关键词开始==============，member={}", member);
        try {
            Map<String, Object> param = Maps.newHashMap();
            param.put("page", (page - 1) * size);
            param.put("size", size);
            param.put("mid", member.getId());
            List<Map<String, Object>> searchKeys = membersearchconsultationService.findSearchKeywordByMember(param);
            log.info("=============获取历史关键词结束==================");
            return ResultUtils.returnSuccess("成功", searchKeys);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.info("=============获取历史关键词结束==================");
            return ResultUtils.returnError("接口调用失败");
        }
    }


    @Override
    public Result clearKeys(Member member) {
        log.info("清空历史关键词接口调用开始，方法入参:",member.toString());
        return membersearchconsultationService.deleteKeys(member) ;
    }
}
