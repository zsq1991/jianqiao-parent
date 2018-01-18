package com.zc.mybatis.dao;

import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName  ConsultationAttachmentDao
 * Date:     2017年5月10日  17:45:41 <br/>
 * @author   dinglanlan
 * @version  咨询图片
 * @see 	 
 */

@MyBatisRepository
public interface ConsultationAttachmentMapper   {


	 List<Map<String, Object>> findConsultationAttachmentByConsultationId(@Param("id") Long id);

	 List<Map<String, Object>> findConsultationAttachmentMVByConsultationId(@Param("id") Long id);

	 List<Map<String,Object>>  getConsultationAttachmentByConsultation(Map<String, Object> map);

	/**
	 * 封面图片
	 * @author huangxin
	 * @data 2018/1/18 15:22
	 * @Description: 封面图片
	 * @Version: 3.2.0
	 * @param mid 资讯id
	 * @return
	 */
	List<Map<String,Object>> getConsultationAttachmentCoverAddressByConsultationId(Long mid);

	/**
	 * 内容详情
	 * @author huangxin
	 * @data 2018/1/18 15:22
	 * @Description: 内容详情
	 * @Version: 3.2.0
	 * @param mid 资讯id
	 * @return
	 */
	List<Map<String,Object>> getConsultationAttachmentDetailByConsultation(Long mid);

	List<Map<String,Object>> getConsultationAttachmentByConsultationType(Map<String, Object> map);

	Map<String,Object> getParentConsultationDetail(@Param("id") Long cid, @Param("type") Integer type);

	Map<String,Object> getCommonConsultation(Long cid);

	List<Map<String,Object>> getConsultationAttachmentByMap(Map<String, Object> map);

	Map<String, Object> findDetailContentByConsultationId(Long id);
	/**
	 * 通过资讯ID获取视频地址
	 * @param id 资讯id
	 * @return
	 */
	public String findConsultationAttachmentVideoAddressByConsultationId(Long id);
}
