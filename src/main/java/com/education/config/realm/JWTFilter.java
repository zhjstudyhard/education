package com.education.config.realm;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-02-04-13-43
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    //进行登录校验，由自定义realm执行该流程
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequestrequest = (HttpServletRequest) request;
        String token = httpServletRequestrequest.getHeader("Authorization");
        JwtToken jwtToken = new JwtToken(token);
        //校验成功后会直接放行返回true
        getSubject(request, response).login(jwtToken);
        return true;
    }

    //判断用户是否允许登录，如果登录则调用executeLogin进行验证token，若正确返回true
    //否则是游客状态则直接返回true放行
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(isLoginAttempt(request, response)){
            try {
                executeLogin(request, response);
                return true;
            }catch (Exception e) {
                return false;
            }

        }
        return true;
    }

    //判断用户是否登录,判断请求头是否包含Authorization字段
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequestrequest = (HttpServletRequest) request;
        String token = httpServletRequestrequest.getHeader("Authorization");
        return token != null;
    }
}
