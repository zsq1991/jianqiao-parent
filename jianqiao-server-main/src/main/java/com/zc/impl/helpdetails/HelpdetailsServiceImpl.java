package com.zc.impl.helpdetails;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.main.service.helpdetails.HelpDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @package : com.zc.impl.helpdetails
 * @progect : jianqiao-parent
 * @Description :
 * @author  by :ZhaoJunBiao
 * @Creation Date ：2018年01月16日15:03
 */
@Service(version = "1.0.0",interfaceClass = HelpDetailsService.class)
@Component
@Transactional(readOnly = true,rollbackFor=Exception.class)
public class HelpdetailsServiceImpl implements HelpDetailsService {
    @Override
    public List<Map<String, Object>> gethelpdetails(Long aLong, Integer page, Integer rows) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getimgList(Long aLong) {
        return null;
    }
}
