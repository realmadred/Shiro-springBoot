package com.example.demo.controller;

import com.example.demo.util.Common;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @auther lf
 * @date 2017/9/13
 * @description 用户管理
 */
@RestController
public class UserController extends BaseController {

    @PostMapping("/login")
    public Map<String,Object> login() throws Exception {
        LOGGER.info("login...");
        // 登录失败从request中获取shiro处理的异常信息。
        Object msg = request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (msg != null) {
            return fail(Common.toString(msg));
        }
        // 获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        System.out.print(attribute);
        return success("");
    }

}
