package com.example.demo.config.shiro;

import com.alibaba.fastjson.JSON;
import com.example.demo.util.Constant;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * lf
 * 权限过滤器
 * 2017-09-22 14:27:33
 */
public class MyPermsAuthenticationFilter extends PermissionsAuthorizationFilter {

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";

    @Override
    protected boolean onAccessDenied(final ServletRequest request, final ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (!XML_HTTP_REQUEST.equalsIgnoreCase(httpServletRequest
                .getHeader(X_REQUESTED_WITH))) {
            // 不是ajax请求
            super.onAccessDenied(request, response);
        } else {
            response.setCharacterEncoding(Constant.UTF_8);
            final PrintWriter writer = response.getWriter();
            try {
                if (subject.getPrincipal() == null) {
                    // 没有登录
                    saveRequest(request);
                    Map<String, Object> map = new HashMap<>();
                    map.put("message", "请登录");
                    map.put("status", HttpStatus.UNAUTHORIZED.value());
                    map.put("code", 0);
                    writer.print(JSON.toJSON(map));
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("message", "没有权限");
                    map.put("status", HttpStatus.FORBIDDEN.value());
                    map.put("code", 0);
                    writer.print(JSON.toJSONString(map));
                }
            } finally {
                writer.flush();
                writer.close();
            }
        }
        return false;
    }
}