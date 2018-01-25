package com.lorne.tx.service;

import com.lorne.tx.service.model.TxServer;
import com.lorne.tx.service.model.TxState;

/**
 * @author by lorne on 2017/7/1.
 */
public interface TxService {
    /**
     * 获取服务
     * @return
     */
    TxServer getServer();

    /**
     * 获取状态
     * @return
     */
    TxState getState();

    /**
     * 发送信息
     * @param model
     * @param msg
     * @return
     */
    boolean sendMsg(String model,String msg);

    /**
     * 检查清空分组
     * @param groupId
     * @param taskId
     * @param isGroup
     * @return
     */
    boolean checkClearGroup(String groupId, String taskId, int isGroup);

    /**
     * 检查分组
     * @param groupId
     * @param taskId
     * @return
     */
    int checkGroup(String groupId, String taskId);
}
