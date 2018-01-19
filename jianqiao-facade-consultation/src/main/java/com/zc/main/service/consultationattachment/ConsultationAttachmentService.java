package com.zc.main.service.consultationattachment;


import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0.0
 * @description ：咨询图片
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/16 11:18
 */
public interface ConsultationAttachmentService {
    /**
     * @description 接口说明 父及资讯
     * @author 王鑫涛
     * @date 15:54 2018/1/19
     * @version 版本号
     * @param map
     * @return
     */
    List<Map<String,Object>> getConsultationAttachmentByConsultationType(Map<String, Object> map);
    /**
     * @param id 资讯id
     * @description ：处理资讯图片
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:19
     * @version 1.0.0
     */
    public List<Map<String, Object>> findConsultationAttachmentByConsultationId(Long id);

   /* public List<Map<String, Object>> findConsultationAttachmentMVByConsultationId(Long id);

    *//**
     * 个人中心 <我的发布>
     *
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
     * @param id 资讯id
     * @description ：取详情内容
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:20
     * @version 1.0.0
     */
    public Map<String, Object> findDetailContentByConsultationId(Long id);

    /**
     * @param id 资讯id
     * @return
     * @description ：通过资讯id获取视频地址
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:19
     * @version 1.0.0
     */
    public String findConsultationAttachmentVideoAddressByConsultationId(Long id);

    /**
     * 个人中心 <我的发布>
     * @author huangxin
     * @data 2018/1/17 11:28
     * @Description: 个人中心 <我的发布>
     * @Version: 3.2.0
     * @param type 类型
     * @param member 用户
     * @param page
     * @param size
     * @return
     */
    Result getConsultationAttachmentByConsultation(String type, Member member, Integer page, Integer size);

    /**
     * 内容详情
     * @author huangxin
     * @data 2018/1/18 15:17
     * @Description: 内容详情
     * @Version: 3.2.0
     * @param sid 资讯id
     * @return
     */
    List<Map<String,Object>> getConsultationAttachmentDetailByConsultation(Long sid);

    /**
     * 封面图片
     * @author huangxin
     * @data 2018/1/18 15:20
     * @Description: 封面图片
     * @Version: 3.2.0
     * @param mid 资讯id
     * @return
     */
    List<Map<String,Object>> getConsultationAttachmentCoverAddressByConsultationId(Long mid);

    /**
     * 专题
     * @author huangxin
     * @data 2018/1/18 16:35
     * @Description: 专题
     * @Version: 3.2.0
     * @param pid id
     * @param lastType 类型
     * @return
     */
    Map<String,Object> getParentConsultationDetail(long pid, Integer lastType);
}
