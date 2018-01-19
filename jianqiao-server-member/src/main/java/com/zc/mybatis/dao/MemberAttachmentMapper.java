package com.zc.mybatis.dao;


import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.attachment.Attachment;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberattachment.MemberAttachment;

import java.util.List;
import java.util.Map;

/**
 * @author xwolf
 * @date 2017-06-19 12:03
 * @since 1.8
 */
@MyBatisRepository
public interface MemberAttachmentMapper {
    /**
     * @description 接口说明  根据id获取用户附件
     * @author 王鑫涛
     * @date 14:27 2018/1/16
     * @version 版本号
     * @param id  资讯id
     * @return
     */
    List<Map<String,Object>> getMemberAttachment(Long id);

    /**
     * 删除用户原有信息
     * @author huangxin
     * @data 2018/1/19 17:32
     * @Description: 删除用户原有信息
     * @Version: 3.2.0
     * @param mid  用户id
     */
    void deleteAttachment(Long mid);

    /**
     * 获取新的用户信息
     * @author huangxin
     * @data 2018/1/19 17:34
     * @Description: 获取新的用户信息
     * @Version: 3.2.0
     * @param id 图片id
     * @return
     */
    Attachment getAttachmentById(long id);

    /**
     * 保存信息
     * @author huangxin
     * @data 2018/1/19 17:35
     * @Description: 保存信息
     * @Version: 3.2.0
     * @param memberAttachment
     */
    void save(MemberAttachment memberAttachment);

}
