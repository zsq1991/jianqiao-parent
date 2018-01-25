package com.zc.main.service.consultationattachment;


import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0.0
 * @description ：咨询图片
 * @author by  : gaoge
 * @Creation Date ： 2018/1/16 11:18
 */
public interface ConsultationAttachmentService {
    /**
     * 父及资讯
     * @param map
     * @return
     * @description 接口说明 父及资讯
     * @author 王鑫涛
     * @date 15:54 2018/1/19
     * @version 版本号
     */
    List<Map<String, Object>> getConsultationAttachmentByConsultationType(Map<String, Object> map);

    /**
     * 处理资讯图片
     * @description 处理资讯图片
     * @author gaoge
     * @date 2018/1/16 11:19
     * @version 1.0.0
     * @param id
     * @return
     */
    public List<Map<String, Object>> findConsultationAttachmentByConsultationId(Long id);

   /* public List<Map<String, Object>> findConsultationAttachmentMVByConsultationId(Long id);

    *//**
     * 个人中心 <我的发布>
     * @param type   类型
     * @param member 用户
     * @param page
     * @param size
     * @return
     * @author huangxin
     * @data 2018/1/9 9:41
     * @Description: 个人中心 <我的发布>
     * @Version: 1.0.0
     *//*
    Result getConsultationAttachmentByConsultation(String type, Member member, Integer page, Integer size);

    Result getParentConsultation(String type, Member member, Integer page, Integer size);*/

    /**
     * 获取详情内容
     * @description 获取详情内容
     * @author gaoge
     * @date 2018-01-25 16:04
     * @version 1.0.0
     * @param id
     * @return
     */
    public Map<String, Object> findDetailContentByConsultationId(Long id);

    /**
     * 通过资讯id获取视频地址
     * @description 通过资讯id获取视频地址
     * @author gaoge
     * @date 2018-01-25 16:04
     * @version 1.0.0
     * @param id
     * @return
     */
    public String findConsultationAttachmentVideoAddressByConsultationId(Long id);

    /**
     * 个人中心 <我的发布>
     *
     * @param type   类型
     * @param member 用户
     * @param page
     * @param size
     * @return
     * @author huangxin
     * @data 2018/1/17 11:28
     * @Description: 个人中心 <我的发布>
     * @Version: 3.2.0
     */
    Result getConsultationAttachmentByConsultation(String type, Member member, Integer page, Integer size);

    /**
     * 内容详情
     * @param sid 资讯id
     * @return
     * @author huangxin
     * @data 2018/1/18 15:17
     * @Description: 内容详情
     * @Version: 3.2.0
     */
    List<Map<String, Object>> getConsultationAttachmentDetailByConsultation(Long sid);

    /**
     * 封面图片
     * @param mid 资讯id
     * @return
     * @author huangxin
     * @data 2018/1/18 15:20
     * @Description: 封面图片
     * @Version: 3.2.0
     */
    List<Map<String, Object>> getConsultationAttachmentCoverAddressByConsultationId(Long mid);

    /**
     * 专题
     * @param pid      id
     * @param lastType 类型
     * @return
     * @author huangxin
     * @data 2018/1/18 16:35
     * @Description: 专题
     * @Version: 3.2.0
     */
    Map<String, Object> getParentConsultationDetail(long pid, Integer lastType);
}
