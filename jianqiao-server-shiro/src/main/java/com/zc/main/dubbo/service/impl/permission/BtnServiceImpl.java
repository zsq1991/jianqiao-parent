package com.zc.main.dubbo.service.impl.permission;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.main.dubbo.service.permission.IBtnService;
import com.zc.main.vo.BtnVO;
import com.zc.mybatis.dao.BtnMapper;

/**
 * @描述：按钮实现层
 */
@Component
@Service(version = "1.0.0",interfaceClass=IBtnService.class)
public class BtnServiceImpl implements IBtnService {

    private static final Logger logger = LoggerFactory.getLogger(BtnServiceImpl.class);

    @Autowired
    private BtnMapper btnMapper;

    @Override
    public List<BtnVO> getBtnListByMenuId(List<Long> menuIds, Long roleId) {
        return btnMapper.getBtnListByMenuIdsAndRoleId(menuIds, roleId);
    }

    @Override
    public List<BtnVO> getBtnList() {
        return btnMapper.getAllBtn();
    }
}
