package com.example.demo.listener;

import com.example.demo.service.PermissionService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @auther lf
 * 需要在application
 * 加注解@ServletComponentScan("com.example.demo.listener")
 * @date 2017/9/21
 * @description 监听器
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent servletContextEvent) {
        final ServletContext servletContext = servletContextEvent.getServletContext();
        final WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        final PermissionService permissionService = webApplicationContext.getBean("permissionService", PermissionService.class);
        permissionService.reloadPermissions();
    }

    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {

    }
}
