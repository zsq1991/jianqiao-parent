package com.zc.main.controller.main;

import com.zc.common.core.config.RedisConfig;
import com.zc.common.core.result.ResultUtils;
import com.zc.common.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 主页控制器
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-6-5 下午9:56:34
 * 
 */
@Controller
@RequestMapping("main")
public class MainController {

    @Autowired
    private RedisConfig redisConfig;

    @RequestMapping("/test")
    @ResponseBody
    public Result test() {
        return ResultUtils.returnSuccess("");
    }

}
