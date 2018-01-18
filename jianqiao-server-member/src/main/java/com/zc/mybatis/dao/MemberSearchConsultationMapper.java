package com.zc.mybatis.dao;

import com.zc.main.entity.member.Member;
import com.zc.main.entity.membersearchconsultation.MemberSearchConsultation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version ： 1.0.0
 * @package : com.zc.mybatis.dao
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : wangxueyang[wxueyanghj@163.com]
 * @Creation Date ：2018年01月17日17:15
 */
public interface MemberSearchConsultationMapper {
    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create:  2018/1/17 17:19
     * @desc: 获取检索历史关键词
     * @version 1.0.0
     * @param map
     * @return
     */
    List<Map<String, Object>> getSearchKeys(Map<String, Object> map);

    /**
     * 获取检索历史关键词存在的个数
     * @param map
     * @return
     */
    Integer getSearchConsultationByInfo(HashMap<String, Object> map);

    /**
     * 保存检索历史
     * @param memberSearchConsultation 用户检索历史关键字
     * @return
     */
    Integer insertSearchConsultation(MemberSearchConsultation memberSearchConsultation);

    /**
     * @description: 清空历史关键词
     * @author:  ZhaoJunBiao
     * @date:  2018/1/18 15:46
     * @version: 1.0.0
     * @param memberId
     */
    void deleteAll(Long  memberId);
}
