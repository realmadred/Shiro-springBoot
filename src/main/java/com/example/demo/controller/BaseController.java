package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther Administrator
 * @date 2017/9/13
 * @description 基础controller
 */
@Controller
public class BaseController {

    /** 结果字符串 */
    private static final String MSG = "message";
    private static final String STATUS = "status";
    private static final String CODE = "code";
    private static final String RESULT = "result";

    /** 成功提示 */
    private static final String STATUS_SUCCESS = "成功";
    private static final int CODE_SUCCESS = 1;

    /** 失败提示 */
    private static final String STATUS_FAIL = "失败";
    private static final int CODE_FAIL = 0;

    /** 错误提示 */
    private static final String STATUS_ERROR = "系统繁忙";
    private static final int CODE_ERROR = -1;

    private static final Map<String,Object> ERROR_RESULT = new HashMap<>();

    static {
        ERROR_RESULT.put(STATUS,STATUS_ERROR);
        ERROR_RESULT.put(CODE,CODE_ERROR);
    }

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest request;

    Map<String,Object> success(Object result){
        Map<String,Object> map = new HashMap<>();
        map.put(MSG,"");
        map.put(STATUS,STATUS_SUCCESS);
        map.put(CODE,CODE_SUCCESS);
        map.put(RESULT,result);
        return map;
    }

    Map<String,Object> fail(String msg){
        Map<String,Object> map = new HashMap<>();
        map.put(MSG,msg);
        map.put(STATUS,STATUS_FAIL);
        map.put(CODE,CODE_FAIL);
        return map;
    }

    Map<String,Object> error(){
        return ERROR_RESULT;
    }

}
