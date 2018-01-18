package com.zc.mybatis.dao;


import com.common.util.mybatis.BasicMapper;
import com.zc.main.dto.attachment.AttachmentDTO;
import com.zc.main.entity.membermsg.MemberMsg;
import org.apache.cxf.message.Attachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wudi
 * @version v1.0
 * @Description: TODO
 * @create-time 2017年6月18日 下午5:38:01
 */
public interface MemberMsgMapper extends BasicMapper<MemberMsg> {
    /**
     * @description 接口说明 通过数据修改系统通知状态
     * @author 王鑫涛
     * @date 11:07 2018/1/18
     * @version 版本号
     * @param types
     * @param readType
     * @param mId 用户id
     * @return
     */
    public int updateMemberMsgReadType(@org.springframework.data.repository.query.Param("types") Integer types,
                                              @org.springframework.data.repository.query.Param("readType") Integer readType,
                                              @org.springframework.data.repository.query.Param("mId") Long mId);
    /**
     * @description 接口说明 根据id获取消息
     * @author 王鑫涛
     * @date 10:58 2018/1/18
     * @version 版本号
     * @param id 消息id
     * @return
     */
    MemberMsg findOne(Long id);
    /**
     * @description 接口说明 获取总的数据
     * @author 王鑫涛
     * @date 10:55 2018/1/18
     * @version 版本号
     * @param member_id
     * @return
     */
    public List<MemberMsg> getRowLockList(@org.springframework.data.repository.query.Param("member_id")Long member_id);

    void deleteMemberMsgByConsulatationId(@Param("memberId") Long memberId, @Param("types") Integer types, @Param("id") Long id);

    /**
     * @description ：取消点赞的时删除对应的memberMsg表格
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/17 10:06
     * @version 1.0.0
     */
    public void deleteMemberMsgBycontentId(@Param("mId") Long mId, @Param("type") Integer type, @Param("commentid") Long commentid);

    /**
     * @description:  查病例图片
     * @author:  ZhaoJunBiao
     * @date:  2018/1/17 18:14
     * @version: 1.0.0
     * @param id
     * @return
     */
    AttachmentDTO getAttamentById(String id);

    /**
     * 查询啊驳回原因
     * @author huangxin
     * @data 2018/1/18 16:32
     * @Description: 查询啊驳回原因
     * @Version: 3.2.0
     * @param conid 资讯id
     * @return
     */
    Map<String,Object> getContentById(Long conid);
}
