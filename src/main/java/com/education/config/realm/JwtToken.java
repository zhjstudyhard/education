package com.education.config.realm;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-02-04-13-28
 */
public class JwtToken implements AuthenticationToken {
    //创建jwt本身的token,但不使用shiro的token
    private String token;

    //构造方法
    public JwtToken(String token) {
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
