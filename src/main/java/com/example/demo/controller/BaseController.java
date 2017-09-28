package com.example.demo.controller;

import com.example.demo.dao.BaseDao;
import com.example.demo.entity.common.Page;
import com.example.demo.util.Common;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
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

    /**
     * 成功返回
     * lf
     * 2017-09-26 17:33:28
     * @param result
     * @return
     */
    Map<String,Object> success(Object result){
        Map<String,Object> map = new HashMap<>();
        map.put(MSG,"");
        map.put(STATUS, STATUS_SUCCESS);
        map.put(CODE,CODE_SUCCESS);
        map.put(RESULT,result);
        return map;
    }

    /**
     * 成功返回
     * lf
     * 2017-09-26 17:33:28
     * @return
     */
    Map<String,Object> success(){
        Map<String,Object> map = new HashMap<>();
        map.put(MSG,"");
        map.put(STATUS,HttpStatus.OK);
        map.put(CODE,CODE_SUCCESS);
        return map;
    }

    /**
     * 失败返回
     * lf
     * 2017-09-26 17:33:38
     * @param msg
     * @return
     */
    Map<String,Object> fail(String msg){
        Map<String,Object> map = new HashMap<>();
        map.put(MSG,msg);
        map.put(STATUS,STATUS_FAIL);
        map.put(CODE,CODE_FAIL);
        return map;
    }

    /**
     * 发送错误
     * lf
     * 2017-09-26 17:33:54
     * @return
     */
    Map<String,Object> error(){
        return ERROR_RESULT;
    }

    /**
     * 获取page
     * lf
     * 2017-09-26 17:33:02
     */
    protected Page getPage(){
        final Integer page = getInteger(request,"page");
        final Integer pageSize = getInteger(request,"pageSize");
        return new Page(page,pageSize);
    }

    /**
     * 从请求参数中获取int值
     * lf
     * 2017-09-26 17:32:50
     * @param request
     * @param param
     * @return
     */
    private Integer getInteger(HttpServletRequest request,String param){
        final String parameter = request.getParameter(param);
        if (NumberUtils.isDigits(parameter)){
            return Integer.valueOf(parameter);
        }
        return 0;
    }

    /**
     * 从请求参数中获取int值
     * lf
     * 2017-09-26 17:32:50
     * @param param
     * @return
     */
    protected String getParam(String param){
        return request.getParameter(param);
    }

    /**
     * 从请求参数中获取int值
     * lf
     * 2017-09-26 17:32:50
     * @param param
     * @return
     */
    protected Integer getParamInt(String param){
        final String parameter = request.getParameter(param);
        if (NumberUtils.isDigits(parameter)){
            return Integer.valueOf(parameter);
        }
        return 0;
    }

    /**
     * 从请求参数中获取int值
     * lf
     * 2017-09-26 17:32:50
     * @param param
     * @return
     */
    protected Long getParamLong(String param){
        final String parameter = request.getParameter(param);
        if (NumberUtils.isDigits(parameter)){
            return Long.valueOf(parameter);
        }
        return 0L;
    }

    /**
     * 从请求参数中获取int值
     * lf
     * 2017-09-26 17:32:50
     * @param param
     * @return
     */
    protected Double getParamDoule(String param){
        final String parameter = request.getParameter(param);
        return Common.getDouble(parameter);
    }


}
