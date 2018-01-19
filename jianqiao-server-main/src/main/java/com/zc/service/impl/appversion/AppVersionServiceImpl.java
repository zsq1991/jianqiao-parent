package com.zc.service.impl.appversion;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.service.appversion.AppVersionService;
import com.zc.mybatis.dao.AppVersionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @package : com.zc.service.impl.appversion
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月18日16:32
 */
@Service(version = "1.0.0",interfaceClass = AppVersionService.class)
@Component
@Transactional(readOnly = true)
public class AppVersionServiceImpl implements AppVersionService {

    private  static Logger logger = LoggerFactory.getLogger(AppVersionServiceImpl.class);

    @Autowired
    private AppVersionMapper appVersionMapper;

    /**
     * @description: 检测软件更新版本信息
     * @author:  ZhaoJunBiao
     * @date:  2018/1/18 16:34
     * @version: 1.0.0
     * @param client_type
     * @param version
     * @return
     */
    @Override
    public Result getAppVersion(String client_type, String version) {
        logger.info("检测软件版本接口调用开始，方法入参："+"设备标识"+client_type+"版本号"+version);
        try {
            //查询相关版本信息
            List<Map<String,Object>> appVersionList = appVersionMapper.getAppVersion();
            if(appVersionList.size()>0){
                Map<String,Object> appVersion=appVersionList.get(0);
                Integer v = Integer.valueOf(appVersion.get("version").toString());
                logger.info("最新版本号"+v);
                Integer v1 = Integer.valueOf(version);
                //设备标识（Android as A、Ios as I）
                if("A".equals(client_type)){
                    //比较版本号是否一致
                    if(version.equals(appVersion.get("version"))){
                        return ResultUtils.returnSuccess("已经是最新版本");
                    }else if("1".equals(appVersion.get("isMustToUpdate"))){
                        return ResultUtils.returnSuccess("当前版本修复重大漏洞", appVersion);

                    }else if((v-v1)>2){
                        return ResultUtils.returnSuccess("当前版本差异过大", appVersion);
                    }else{
                        return ResultUtils.returnSuccess("当前版本需要更新", appVersion);
                    }
                }else{
                    return ResultUtils.returnError("设备标识有误");
                }
            }else{
                return ResultUtils.returnError("已经是最新版本");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("版本强更方法执行异常");
            return ResultUtils.returnError(e.getMessage());
        }
    }
}
