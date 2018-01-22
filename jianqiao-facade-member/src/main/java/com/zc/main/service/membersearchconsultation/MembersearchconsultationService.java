package com.zc.main.service.membersearchconsultation;

import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : wangxueyang[wxueyanghj@163.com]
 * @version ： 1.0.0
 * @package : com.zc.main.service.membersearchconsultation
 * @progect : jianqiao-parent
 * @Description :
 * @Creation Date ：2018年01月17日17:08
 */
public interface MembersearchconsultationService {

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param map
     * @return
     * @create: 2018/1/22 9:37
     * @desc: 获取用户历史检索关键词
     * @version 1.0.0
     */
    List<Map<String, Object>> findSearchKeywordByMember(Map<String, Object> map);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param map
     * @return
     * @create: 2018/1/22 9:37
     * @desc: 获取资讯信息
     * @version 1.0.0
     */
    Integer getSearchConsultationByInfo(HashMap<String, Object> map);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param id 用户id
     * @param info 关键词
     * @return
     * @create: 2018/1/22 9:38
     * @desc: 保存用户检索关键词
     * @version 1.0.0
     */
    Result saveMemberSearchConsultation(Long id, String info);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create: 2018/1/22 9:39
     * @desc: 删除关键词
     * @version 1.0.0
     * @param member
     * @return
     */
    Result deleteKeys(Member member);
}
