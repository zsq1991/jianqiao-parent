package com.zc.mybatis.dao.collectionconsulation;

import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.collectionconsultation.CollectionConsultation;

import java.util.List;
import java.util.Map;

/**
 * @package : com.zc.mybatis.dao.collectionconsulation
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : asus 王鑫涛
 * @Creation Date ：2018年01月17日16:59
 */
@MyBatisRepository
public interface CollectionConsulationMapper extends BasicMapper<CollectionConsultation>{
	/**
	 *
	 *@description 接口说明  通过资讯id获取数据
	 * @author 王鑫涛
	 * @date 17:06 2018/1/17
	 * @version 版本号
	 * @param id 资讯id
	 * @return
	 */
	public List<Map> getMemberIdByConsultationId(Long id);
	/**
	 *
	 *@description方法说明 通过资讯主题id获取所有主题下资讯的id
	 * @author 王鑫涛
	 * @date  15:31  2018/1/19
	 * @version 版本号
	 * @param id 资讯id
	 * @return
	 */
	public List<Map> getConsultationIdAllByconsultationId(Long id);
}
