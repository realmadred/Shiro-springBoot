package com.example.demo.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Map<String,Object> login(Map<String, Object> map) throws Exception {
        LOGGER.info("login...");
        // 登录失败从request中获取shiro处理的异常信息。
        String exception = (String) request.getAttribute(SHIRO_LOGIN_FAILURE);
        LOGGER.info("exception={}", exception);
        String msg ;
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                msg = "账号不存在";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                msg = "密码不正确";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                msg = "验证码错误";
            } else {
                return error();
            }
            return fail(msg);
        }
        return success("");
    }

}
