package com.zc.mybatis.dao.specialist;

import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.specialists.Specialist;

import java.util.List;
import java.util.Map;

/**
 * @package : com.alqsoft.dao.specialist
 * @progect : jianqiao-parent
 * @Description :专家模块数据访问层
 * @Created by : 朱军
 * @Creation Date ：2018年01月09日9:22
 */
@MyBatisRepository
public interface SpecialistsMapper {

    /**
     * @description ：根据专家ID获取专家信息
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/9 9:51
     * @param specialistId
     * @return
     */
    public Map<String,Object> getSpecialistDetail(Long specialistId);

    /**
     * @description ：根据科室获取咨询信息列表
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/9 9:49
     * @param title
     * @return
     */
    public List<Specialist> queryConsultationList(String title);

    /**
     * @description ：根据专家ID获取荣誉图片集合
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/9 15:07
     * @param id
     * @return
     */
    List<String> getDoctorAttachment(Long id);

    /**
     * @description ：获取专家列表
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/9 16:51
     * @param param
     * @return
     */
    List<Map<String,Object>> getSpecialistData(Map<String, Object> param);

    /**
     * @description ：获取高手列表
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/9 18:03
     * @param param
     * @return
     */
    List<Map<String,Object>> getDoctorData(Map<String, Object> param);

    /**
     * @description ：获取专家总数
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/11 10:00
     * @return
     */
    public Integer getSpecialistCount();

    /**
     * @description ：获取高手总数
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/11 10:01
     * @return
     */
    public Integer getDoctorCount();
}
