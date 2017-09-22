package com.example.demo.controller;

import com.example.demo.util.Common;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        final Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(parameterMap);
        if (msg != null) {
            return fail(Common.toString(msg));
        }
        return success("");
    }

}
