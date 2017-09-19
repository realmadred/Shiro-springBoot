package com.example.demo.config.shiro;

import com.example.demo.service.PermissionService;
import com.example.demo.util.Common;
import com.example.demo.util.Constant;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.hazelcast.cache.HazelcastCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Configuration
public class ShiroConfig {

    @Resource
    private PermissionService permissionService;

//    @Bean
//    public CredentialsMatcher myMatcher(){
//        return new MyCredentialsMatcher();
//    }

    @Bean
    public CredentialsMatcher getMatcher(){
        return new HashedCredentialsMatcher("MD5");
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        final MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(getMatcher());
        return myShiroRealm;
    }

    @Bean
    public CacheManager cacheManager(){
        return new HazelcastCacheManager();
    }

    @Bean
    public SessionDAO sessionDAO(CacheManager cacheManager){
        final EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setCacheManager(cacheManager);
        return sessionDAO;
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
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

        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");

        final List<Map<String, Object>> maps = permissionService.findAll();
        if (!CollectionUtils.isEmpty(maps)){
            maps.forEach(map ->{
                final String permission = Common.getMapString(map, "permission");
                final String url = Common.getMapString(map, "url");
                if (!Common.hasEmpty(permission,url)){
                    filterChainDefinitionMap.put("/"+url,new StringJoiner("")
                            .add(Constant.PERMS_STR)
                            .add(Constant.SECTION_PREFIX)
                            .add(permission).add(Constant.SECTION_SUFFIX).toString());
                }
            });
        }

        //放在最后
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        return shiroFilterFactoryBean;
    }

}