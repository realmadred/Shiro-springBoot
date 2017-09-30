package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @auther lf
 * @date 2017/9/18
 * @description 常量
 */
public class Constant {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constant.class);

    private static final String SHIRO_PROPERTIES_FILE = "shiro.properties";
    private static final Properties SHIRO_PROPERTIES = new Properties();

    static {
        try {
            SHIRO_PROPERTIES.load(Constant.class.getClassLoader().getResourceAsStream(SHIRO_PROPERTIES_FILE));
        } catch (IOException e) {
            LOGGER.error("加载配置{}文件失败","shiro.properties");
        }
    }

    /**权限字符串*/
    public static final String PERMS_CHAIN = "perms";
    public static final String SECTION_PREFIX = "[";
    public static final String SECTION_SUFFIX = "]";
    public static final String PERMS_PREFIX = PERMS_CHAIN +SECTION_PREFIX;
    public static final String ALL_CHAIN = "/**";
    public static final String ANON_CHAIN = "anon";
    public static final String AUTHC_CHAIN = "authc";
    public static final String LOGOUT_CHAIN = "logout";
    public static final String ROLE_ADMIN = "admin";

    // 权限key前缀
    public static final String PRINCIPAL_PREFIX = "principal_prefix-";

    /**默认字符集*/
    public static final String UTF_8 = "UTF-8";

    /**不需要拦截的资源*/
    public static final String SHIRO_NOT_CHAIN = SHIRO_PROPERTIES.getProperty("shiro.not.chain","/static/**,/html/login.html,/html/404Page.html,/html/500Page.html,/fonts/**,/css/**,/js/**,/i/**,/img/**,/*.psd");

}
