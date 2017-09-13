package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther Administrator
 * @date 2017/9/13
 * @description 基础controller
 */
@Controller
public class BaseController {

    // shiro异常类的全类名
    protected static final String SHIRO_LOGIN_FAILURE = "shiroLoginFailure";

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest request;

}
