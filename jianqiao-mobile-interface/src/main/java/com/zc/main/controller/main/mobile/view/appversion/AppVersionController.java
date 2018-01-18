package com.zc.main.controller.main.mobile.view.appversion;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.result.Result;
import com.zc.main.service.appversion.AppVersionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @description: 软件更新版本信息
 * @author:  ZhaoJunBiao
 * @date:  2018/1/18 16:30
 * @version: 1.0.0
 */
@RequestMapping("mobile/view/appversion")
@Controller
public class AppVersionController {
	
	@DubboConsumer(version = "1.0.0",timeout = 30000)
	private AppVersionService appVersionService;
	
	/**
	 * 检测软件更新版本信息
	 * @return
	 */
	@RequestMapping(value="getappversion",method= RequestMethod.POST)
	@ResponseBody
	public Result getappversion(@RequestParam(value="client_type")String client_type,
								 @RequestParam(value="version")String version){
		
		Result result =appVersionService.getAppVersion(client_type,version);
		
		return result;
	}
	
}
