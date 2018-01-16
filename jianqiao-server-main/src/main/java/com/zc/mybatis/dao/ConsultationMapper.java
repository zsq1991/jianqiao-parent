package com.zc.mybatis.dao;


import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.consultation.Consultation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description ：咨询信息
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/16 10:48
 * @version 1.0.0
 */
@MyBatisRepository
public interface ConsultationMapper extends BasicMapper<Consultation> {

	/**
	 * APP首页咨询搜索
	 * @return
	 */
	public List<Map<String, Object>> findconsultationinfo(HashMap<String, Object> param);
	

	Map<String,Object> getConsultationById(String id);
	
	Map<String,Object> getConsulationByIdaaa(Long id);
	/**
	 * 用户个人中心   发布管理  -- 资讯搜索
	 * @return
	 */
	public List<Map<String, Object>> findConsultationAll(HashMap<String, Object> param);
	/**
	 * 根据phone和uuid查询用户信息
	 * @return
	 */
	public List<Map<String, Object>> getConsultationByPhone(HashMap<String, Object> param);
	
	/**
	 * 根据咨询id查询该想咨询是否存在或删除
	 * @param consultationid
	 * @return
	 */
	public int findHasConsultationById(Long consultationid);

	/**
	 * 根据访谈或口述的id查询其子类
	 * @return
	 */
	public List<Map<String, Object>> findConsultationChidById(Long id);

	/**
	 * APP求助  内容根据关键词搜索
	 * @return
	 */
	public List<Map<String, Object>> findConsultationInfoHelp(HashMap<String, Object> param);

	/**
	 * APP民间高手  内容根据关键词搜索
	 * @return
	 */
	public List<Map<String, Object>> findConsultationInfoPeople(HashMap<String, Object> param);

	/**
	 * 个人中心  我的发布
	 * @author huangxin
	 * @data 2018/1/8 14:14
	 * @Description: 个人中心  我的发布
	 * @Version: 1.0.0
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findConsultationAllByMember(HashMap<String, Object> param);

	/**
	 * 访谈详情页   点用户头像查看所有
	 * @return
	 */
	public List<Map<String, Object>> findConsultationAllByTouxiang(HashMap<String, Object> param);


	public List<Map<String, Object>> findConsultationByIdAll(Long typeId);
	/**
	 * 查看全部  分页加载
	 * @return
	 */

	public List<Map<String, Object>> findConsultationAllByFive(HashMap<String, Object> param);

	List<Map<String,Object>> getConsultationByMap(Map<String, Object> map);

	/**
	 * APP首页搜索接口
	 * @return
	 */
	public List<Map<String, Object>> searchConsultationInfo(HashMap<String, Object> param);
	/**
	 * APP首页搜索接口---分词后进行查询
	 * @return
	 */
	public List<Map<String, Object>> searchConsultationByInfoList(Map<String, Object> m);

	/**
	 * 查询资讯子类
	 * @author huangxin
	 * @data 2018/1/8 14:18
	 * @Description: 查询资讯子类
	 * @Version: 1.0.0
	 * @param id 资讯id
	 * @return
	 */
	public List<Map<String, Object>> findConsultationChidByIdByMember(Long id);


	public Integer getCountById(Long id);
	
	/**
	 * 通过收藏id获取资讯的信息
	 * @param param
	 * @return
	 */
	public List<Map> getConsulationIdList(Map<String, Object> param);
	
 }
