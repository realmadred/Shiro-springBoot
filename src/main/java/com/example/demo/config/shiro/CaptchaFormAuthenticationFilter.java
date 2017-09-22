package com.example.demo.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * lf
 * 权限过滤器
 * 2017-09-22 14:27:33
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";

    /*
     *	主要是针对登入成功的处理方法。对于请求头是AJAX的之间返回JSON字符串。
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
            throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (!XML_HTTP_REQUEST.equalsIgnoreCase(httpServletRequest
                .getHeader(X_REQUESTED_WITH))) {// 不是ajax请求
            issueSuccessRedirect(request, response);
        }else {
            Object principal = token.getPrincipal();
            System.out.print(principal);
        }
        return true;
    }

    /**
     * 主要是处理登入失败的方法
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        if (!XML_HTTP_REQUEST.equalsIgnoreCase(((HttpServletRequest) request)
                .getHeader(X_REQUESTED_WITH))) {// 不是ajax请求
            setFailureAttribute(request, e);
            return true;
        }
        String message = e.getClass().getSimpleName();
        if ("IncorrectCredentialsException".equals(message)) {
            message = "密码错误";
        } else if ("UnknownAccountException".equals(message)) {
            message = "账号不存在";
        } else if ("LockedAccountException".equals(message)) {
            message = "账号被锁定";
        } else {
            message = "未知错误";
        }
        request.setAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, message);
        return true;
    }

    /**
     * 所有请求都会经过的方法。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            }
            return true;
        } else {
            if (!XML_HTTP_REQUEST.equalsIgnoreCase(((HttpServletRequest) request)
                    .getHeader(X_REQUESTED_WITH))) {// 不是ajax请求
                saveRequestAndRedirectToLogin(request, response);
                return false;
            }
            return true;
        }
    }

    @Override
    protected boolean isLoginRequest(final ServletRequest request, final ServletResponse response) {
        return pathsMatch(getLoginUrl(), request) || pathsMatch("/login", request);
    }

}