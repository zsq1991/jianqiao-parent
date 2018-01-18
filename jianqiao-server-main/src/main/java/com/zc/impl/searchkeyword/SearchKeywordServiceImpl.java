package com.zc.impl.searchkeyword;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.main.service.searchkeyword.SearchKeywordService;
import com.zc.mybatis.dao.SearchKeywordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0.0
 * @description ：热门搜索
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/18 13:35
 */
@Component
@Service(version = "1.0.0", interfaceClass = SearchKeywordService.class)
@Transactional(readOnly = true)
public class SearchKeywordServiceImpl implements SearchKeywordService {

    private static Logger log = LoggerFactory.getLogger(SearchKeywordServiceImpl.class);

    @Autowired
    private SearchKeywordMapper searchKeywordDao;

    @Override
    public List<Map<String, Object>> getKeys() {
        return searchKeywordDao.getKeys();
    }
}