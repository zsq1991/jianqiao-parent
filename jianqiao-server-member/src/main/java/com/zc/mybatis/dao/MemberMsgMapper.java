package com.zc.mybatis.dao;


import com.zc.main.dto.attachment.AttachmentDTO;
import com.zc.main.entity.membermsg.MemberMsg;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author wudi
 * @version v1.0
 * @Description: TODO
 * @create-time 2017年6月18日 下午5:38:01
 */
public interface MemberMsgMapper extends BaseMapper<MemberMsg> {

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
}
