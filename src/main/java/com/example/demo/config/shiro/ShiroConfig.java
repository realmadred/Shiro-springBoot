package com.example.demo.config.shiro;

import com.example.demo.config.redis.RedisCacheManager;
import com.example.demo.util.Constant;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class ShiroConfig {

//    @Bean
//    public CredentialsMatcher myMatcher(){
//        return new MyCredentialsMatcher();
//    }

    @Bean
    public CredentialsMatcher getMatcher(){
        return new HashedCredentialsMatcher("MD5");
    }

    @Bean
    public AuthorizingRealm myShiroRealm() {
        final AuthorizingRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(getMatcher());
        return myShiroRealm;
    }

    @Bean("shiroCacheManager")
    public CacheManager shiroCacheManager(RedisTemplate<Object,Object> template){
        final RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setTemplate(template);
        return redisCacheManager;
    }

    @Bean
    @Primary
    public SessionFactory sessionFactory(){
        return new MySessionFactory();
    }

    @Bean
    public SessionDAO sessionDAO(@Qualifier("shiroCacheManager") CacheManager cacheManager){
        final MySessionDao sessionDAO = new MySessionDao();
        sessionDAO.setCacheManager(cacheManager);
        sessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        return sessionDAO;
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO,SessionFactory sessionFactory){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionFactory(sessionFactory);
        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager(CacheManager cacheManager,SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setCacheManager(cacheManager);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/html/login.html");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/html/index.html");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/html/403.html");
        shiroFilterFactoryBean.getFilters().put(Constant.AUTHC_CHAIN,new CaptchaFormAuthenticationFilter());
        shiroFilterFactoryBean.getFilters().put(Constant.PERMS_CHAIN,new MyPermsAuthenticationFilter());
        return shiroFilterFactoryBean;
    }

}