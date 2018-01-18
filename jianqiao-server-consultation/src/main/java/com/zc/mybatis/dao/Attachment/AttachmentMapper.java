package com.zc.mybatis.dao.Attachment;

import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.attachment.Attachment;
import org.apache.ibatis.annotations.Param;

/**
 * @package : com.zc.mybatis.dao.Attachment
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : asus 王鑫涛
 * @Creation Date ：2018年01月18日16:01
 */
@MyBatisRepository
public interface AttachmentMapper extends BasicMapper<Attachment>{
	/**
	 * @description 接口说明
	 * @author 王鑫涛
	 * @date 16:04 2018/1/18
	 * @version 版本号
	 * @param id
	 * @return
	 */
	Attachment findOne(@Param("id") Long id);
}
