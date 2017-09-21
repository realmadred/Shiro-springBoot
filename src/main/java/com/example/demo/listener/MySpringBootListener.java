package com.example.demo.listener;

import com.example.demo.service.PermissionService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @auther lf
 * @date 2017/9/21
 * @description 使用spring的监听器
 */
public class MySpringBootListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent applicationReadyEvent) {
        final ConfigurableApplicationContext applicationContext = applicationReadyEvent.getApplicationContext();
        final PermissionService permissionService = applicationContext.getBean("permissionService", PermissionService.class);
        permissionService.reloadPermissions();
    }
}
