package com.zc.main.service.helpdetails;

import java.util.List;
import java.util.Map;

/**
 * @package : com.zc.main.service.helpdetails
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月16日14:55
 */
public interface HelpDetailsService {
    List<Map<String,Object>> gethelpdetails(Long aLong, Integer page, Integer rows);

    List<Map<String,Object>> getimgList(Long aLong);
}
