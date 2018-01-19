package com.zc.mybatis.dao;

import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.attachment.Attachment;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author
 * @package : com.zc.mybatis.dao
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : 三只小金
 * @Creation Date ：2018年01月18日17:28
 */
@MyBatisRepository
public interface AttachmentMapper extends BasicMapper<Attachment>{

}
