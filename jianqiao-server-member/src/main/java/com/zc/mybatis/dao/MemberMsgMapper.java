package com.zc.mybatis.dao;


import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.dto.attachment.AttachmentDTO;
import com.zc.main.entity.consultationattachment.ConsultationAttachment;
import com.zc.main.entity.membermsg.MemberMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wudi
 * @version v1.0
 * @Description: TODO
 * @create-time 2017年6月18日 下午5:38:01
 */
@MyBatisRepository
public interface MemberMsgMapper extends BasicMapper<MemberMsg> {
    /**
     * @description 接口说明 通过数据修改系统通知状态
     * @author 王鑫涛
     * @date 11:07 2018/1/18
     * @version 版本号
     * @param types 通知类型
     * @param readType  0或者null未阅读，1阅读
     * @param mId 用户id
     * @return
     */
    public int updateMemberMsgReadType(@Param("types") Integer types,
                                              @Param("readType") Integer readType,
                                              @Param("mId") Long mId);
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
     * @param memberId  被通知的用户id
     * @return
     */
    public List<MemberMsg> getRowLockList(@org.springframework.data.repository.query.Param("member_id")Long memberId);

    void deleteMemberMsgByConsulatationId(@Param("memberId") Long memberId, @Param("types") Integer types, @Param("id") Long id);

    /**
     * @description ：取消点赞的时删除对应的memberMsg表格
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/17 10:06
     * @version 1.0.0
     */
    public void deleteMemberMsgBycontentId(@Param("mId") Long mId, @Param("type") Integer type, @Param("commentid") Long commentId);

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
     *
     * @description 接口说明 获取审核的通知列表
     * @author 王鑫涛
     * @date 15:49 2018/1/19
     * @version 版本号
     * @param mId 登录者id
     * @param page  当前页
     * @param rows  每页显示的数量
     * @param type 审核通知标识
     * @return
     */
    public List<Map> getCheckInformList(@Param("mId")Long mId, @Param("page")Integer page, @Param("rows")Integer rows, @Param("type")Integer type);
    /**
     *
     * @description 接口说明 获取赞的通知列表
     * @author 王鑫涛
     * @date 15:49 2018/1/19
     * @version 版本号
     * @param mId 登陆者id
     * @param page 当前页
     * @param rows  每页显示的数量
     * @return
     */
    public List<Map> getSupportInformList(@Param("mId")Long mId,@Param("page")Integer page,@Param("rows")Integer rows);
    /**
     *
     * @description 接口说明 获取评论资讯赞
     * @author 王鑫涛
     * @date 15:50 2018/1/19
     * @version 版本号
     * @param comId 评论id
     * @param memId 点赞者id
     * @param mLId 登录者的id
     * @return
     */
    public List<Map> getCommentSupportInformList(@Param("comId")Long comId,@Param("memId")Long memId,@Param("mLId")Long mLId);
    /**
     *
     * @description 接口说明 获取资讯赞
     * @author 王鑫涛
     * @date 15:50 2018/1/19
     * @version 版本号
     * @param conId 资讯id
     * @param memId 点赞者id
     * @param mLId 登录者的id
     * @return
     */
    public List<Map> getConsulatationSupportInformList(@Param("conId")Long conId,@Param("memId")Long memId,@Param("mLId")Long mLId);
    /**
     *
     * @description 接口说明 获取人评论者列表的数据
     * @author 王鑫涛
     * @date 15:50 2018/1/19
     * @version 版本号
     * @param mId 登录者的id
     * @param page
     * @param rows
     * @return
     */
    public List<Map> getCommentInformList(@Param("mId")Long mId,@Param("page")Integer page,@Param("rows")Integer rows);
    /**
     *
     * @description 接口说明 获取评论回复
     * @author 王鑫涛
     * @date 15:50 2018/1/19
     * @version 版本号
     * @param comId 资讯id
     * @param memId 点赞者id
     * @param mLId 登录者的id
     * @return
     */
    public List<Map> getCommentDiscussInformList(@Param("comId")Long comId,@Param("memId")Long memId,@Param("mLId")Long mLId);
    /**
     *
     * @description 接口说明 获取资讯评论
     * @author 王鑫涛
     * @date 15:51 2018/1/19
     * @version 版本号
     * @param conId 资讯id
     * @param memId 评论者id
     * @param mLId 登录者的id
     * @return
     */
    public List<Map> getConsulatationDiscussInformList(@Param("comId")Long comId,@Param("conId")Long conId,@Param("memId")Long memId,@Param("mLId")Long mLId);
    /**
     *
     * @description 接口说明 通过资讯的id获取Attachment
     * @author 王鑫涛
     * @date 15:51 2018/1/19
     * @version 版本号
     * @param id 资讯的id
     * @return
     */
    public ConsultationAttachment getAttachmentByconId(@Param("id") Long id);
    /**
     *
     * @description 接口说明 获取回答者的数据
     * @author 王鑫涛
     * @date 15:52 2018/1/19
     * @version 版本号
     * @param conId  资讯id
     * @param memId  评论者的id
     * @param mLId   登录着的id
     * @return
     */
    public List<Map> getReplyDiscussInformList(@Param("conId")Long conId,@Param("memId")Long memId,@Param("mLId")Long mLId);

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

    /**
     * 保存 memberMsg
     * @param memberMsg
     * @return
     */
    int save(MemberMsg memberMsg);

    /**
     * 判断是否是审核失败修改状态,@wudi，如果是审核失败的修改删除通知的memberMsg,1认证驳回  2内容驳回
     * @author huangxin
     * @data 2018/1/19 17:51
     * @Description: 判断是否是审核失败修改状态,@wudi，如果是审核失败的修改删除通知的memberMsg,1认证驳回  2内容驳回
     * @Version: 3.2.0
     * @param id
     * @param i
     */
    void deleteMemberMsgByMId(Long id, int i);
}
