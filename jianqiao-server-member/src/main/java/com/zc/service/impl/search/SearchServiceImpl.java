package com.zc.service.impl.search;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.service.membersearchconsultation.MembersearchconsultationService;
import com.zc.main.service.search.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @package : com.zc.service.impl.search
 * @progect : jianqiao-parent
 * @Description :历史搜索和热门搜索
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月18日15:15
 */
@Service(version = "1.0.0",interfaceClass = SearchService.class)
@Component
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {
    private  static Logger logger = LoggerFactory.getLogger(SearchService.class);

    private MembersearchconsultationService membersearchconsultationService;

    /**
     * @description:  查看全部历史关键词列表(已登录)
     * @author:  ZhaoJunBiao
     * @date:  2018/1/18 15:25
     * @version: 1.0.0
     * @param member
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result getHistoryKeys(Member member, Integer page, Integer size) {
        logger.info("查看全部历史关键词列表(已登录)接口调用开始，方法入参：",member.toString());
        try {
            Map<String,Object> param = Maps.newHashMap();
            param.put("page",(page-1)*size);
            param.put("size",size);
            param.put("mid",member.getId());
            List<Map<String,Object>> searchKeys = membersearchconsultationService.findSearchKeywordByMember(param);
            return ResultUtils.returnSuccess("成功",searchKeys);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResultUtils.returnError("接口调用失败");
        }
    }
}
