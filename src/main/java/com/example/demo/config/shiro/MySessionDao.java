package com.example.demo.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;

/**
 * @auther Administrator
 * @date 2017/9/20
 * @description 描述
 */
public class MySessionDao extends EnterpriseCacheSessionDAO {

    @Override
    protected void assignSessionId(final Session session, final Serializable sessionId) {
        ((MySession) session).setId(sessionId);
    }
}
