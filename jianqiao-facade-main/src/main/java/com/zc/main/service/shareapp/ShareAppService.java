package com.zc.main.service.shareapp;

import java.util.List;
import java.util.Map;

/**
 * @package : com.zc.main.service.shareapp
 * @progect : jianqiao-parent
 * @Description :
 * @author  by :ZhaoJunBiao
 * @Creation Date ：2018年01月16日14:53
 */
public interface ShareAppService {
    Map<String,Object> getConsultationnow(String id);

    Map<String,Object> getConsultation(Long id);

    Map<String,Object> getConsultationTop(String id);

    List<Map<String,Object>> getConsultationList(String s);

    List<Map<String,Object>> getFTDetailList(String s);

    List<Map<String,Object>> getConsultationDetail(String s);

    List<Map<String,Object>> getreplyDetail(String commentid);

    Map<String,Object> getFTPLNum(String s);

    Map<String,Object> getHelpAuthor(String id);

    List<Map<String,Object>> getImageList(String id);

    List<Map<String,Object>> getHelpAuthorIdList(String id);

    Map<String,Object> getAuthorUserList(String bid);

    List<Map<String,Object>> getAuthorList(String bid);
}
