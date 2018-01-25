package com.lorne.tx.service;

import com.lorne.tx.mq.model.TxGroup;

/**
 * @author by lorne on 2017/6/7.
 */

public interface TxManagerService {


    /**
     * 创建事物组
     * @return
     */
    TxGroup createTransactionGroup();


    /**
     * 添加事务组子对象
     * @param groupId
     * @param uniqueKey
     * @param taskId
     * @param isGroup
     * @param modelName
     * @return
     */
    TxGroup addTransactionGroup(String groupId,String uniqueKey, String taskId, int isGroup,String modelName);


    /**
     * 检查事务组
     * @param groupId
     * @param taskId
     * @return
     */
    int checkTransactionGroup(String groupId,String taskId);

    /**
     * 关闭事务组
     * @param groupId
     * @param state
     * @return
     */
    boolean closeTransactionGroup(String groupId,int state);

    /**
     * 处理事务组
     * @param txGroup
     * @param hasOk
     */
    void dealTxGroup(TxGroup txGroup, boolean hasOk );

    /**
     * 删除事务组
     * @param txGroup
     */
    void deleteTxGroup(TxGroup txGroup);

    /**
     * 获取延迟时间
     * @return
     */
    int getDelayTime();

    /**
     * 清理唤醒数据
     * @param time
     */
    void clearNotifyData(int time);

    /**
     * 检查清理事务组
     * @param groupId
     * @param taskId
     * @param isGroup
     * @return
     */
    boolean checkClearGroup(String groupId, String taskId, int isGroup);
}
