package com.example.demo.config;

import com.example.demo.service.RoleService;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.Common;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyShiroRealm.class);

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LOGGER.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        final Map<String,Object> map = (Map<String,Object>) principals.getPrimaryPrincipal();
        final Integer id = Common.getMapInteger(map,"id");
        // 查询用户角色
        final List<Map<String, Object>> roles = userInfoService.findRolesById(id);
        for(Map<String, Object> role:roles){
            authorizationInfo.addRole(Common.getMapString(role,"role"));
            final int roleId = Common.getMapInteger(role, "id");
            // 查询权限
            final List<Map<String, Object>> permissions = roleService.findPermissionsById(roleId);
            for(Map<String, Object> p:permissions){
                authorizationInfo.addStringPermission(Common.getMapString(p,"permission"));
            }
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        LOGGER.info("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        LOGGER.info("Credentials:{}",token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        final Map<String, Object> userInfo = userInfoService.findByUsername(username);
        LOGGER.info("userInfo={}",userInfo);
        if(CollectionUtils.isEmpty(userInfo)){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                Common.getMapString(userInfo,"password"), //密码
                ByteSource.Util.bytes(username+"@"+Common.getMapString(userInfo,"salt")),//salt=username@salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}