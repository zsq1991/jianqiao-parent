package com.zc.service.confige;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;


/**
 * @author system
 */
public class MySessionListener extends SessionListenerAdapter {
    @Override
    public void onExpiration(Session session) {//会话过期时触发
        System.out.println("会话过期：" + session.getId() + "回到登录页面！");
    }
}
