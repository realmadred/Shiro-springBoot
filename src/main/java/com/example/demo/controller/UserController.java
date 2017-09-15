package com.example.demo.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @auther Administrator
 * @date 2017/9/13
 * @description 描述
 */
@Controller
public class UserController extends BaseController {

    @GetMapping({"/","/index"})
    public String index(){
        return"/index";
    }

    @GetMapping("/403")
    public String unauthorizedRole(){
        LOGGER.info("------没有权限-------");
        return "403";
    }

    @RequestMapping("/login")
    public String login(Map<String, Object> map) throws Exception {
        LOGGER.info("login...");
        // 登录失败从request中获取shiro处理的异常信息。
        String exception = (String) request.getAttribute(SHIRO_LOGIN_FAILURE);
        LOGGER.info("exception={}", exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                LOGGER.info("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                LOGGER.info("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                LOGGER.info("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> " + exception;
                LOGGER.info("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "/login";
    }

    /**
     * 用户查询.
     * @return
     */
    @GetMapping("/userList")
    @RequiresPermissions("userInfo:view")//权限管理;
    public String userInfo(){
        return "userInfo";
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd(){
        return "userInfoAdd";
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")//权限管理;
    public String userDel(){
        return "userInfoDel";
    }
}
