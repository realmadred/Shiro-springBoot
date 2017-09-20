package com.example.demo.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

/**
 * @auther Administrator
 * @date 2017/9/20
 * @description 会话工厂
 */
public class MySessionFactory implements SessionFactory {

    @Override
    public Session createSession(final SessionContext initData) {
        if (initData != null) {
            String host = initData.getHost();
            if (host != null) {
                return new MySession(host);
            }
        }
        return new MySession();
    }
}
