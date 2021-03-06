package com.zc.mybatis.dao.attachment;

import com.zc.common.core.basemapper.BaseMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.attachment.Attachment;
import org.apache.ibatis.annotations.Param;

/**
 * @package : com.zc.mybatis.dao.attachment
 * @progect : jianqiao-parent
 * @Description :
 * @author by : asus 王鑫涛
 * @Creation Date ：2018年01月22日16:07
 */
@MyBatisRepository
public interface AttachmentMapper extends BaseMapper<Attachment> {
	/**
	 * 根据附件id获取系统附件
	 * @description 接口说明 根据附件id获取系统附件
	 * @author 王鑫涛
	 * @date 16:04 2018/1/18
	 * @version 版本号
	 * @param id 系统附件id
	 * @return
	 */
	Attachment findOne(@Param("id") Long id);

}
