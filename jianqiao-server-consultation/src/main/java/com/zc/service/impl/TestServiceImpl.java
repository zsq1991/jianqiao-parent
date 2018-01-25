package com.zc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.main.entity.TestTable;
import com.zc.main.service.TestService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description : 测试
 * @author  by : tenghui
 * @Creation Date ：2018年01月15日17:53
 */

@Component
@Transactional(rollbackFor = Exception.class)
@Service(version = "1.0.0", interfaceClass = TestService.class)
public class TestServiceImpl implements TestService {
    @Override
    public boolean saveAndModify(TestTable t) {
        return false;
    }

    @Override
    public TestTable get(Long id) {
        return null;
    }
}
