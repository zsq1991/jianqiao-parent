package com.zc.main.service.appversion;

import com.zc.common.core.result.Result;

/**
 * @package : com.zc.main.service.appversion
 * @progect : jianqiao-parent
 * @Description :
 * @author by :ZhaoJunBiao
 * @Creation Date ：2018年01月18日16:32
 */
public interface AppVersionService {
    /**
     * @description: 检测软件更新版本信息
     * @author:  ZhaoJunBiao
     * @date:  2018/1/18 16:33
     * @version: 1.0.0
     * @param clientType
     * @param version
     * @return
     */
    Result getAppVersion(String clientType, String version);
}
