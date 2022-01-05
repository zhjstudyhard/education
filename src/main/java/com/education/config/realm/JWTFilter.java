package com.education.config.realm;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.config.SecurityBean;
import com.education.constant.Constant;
import com.education.util.JWTUntils;
import com.education.util.RedisUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                /*
                 *注意这里捕获的异常其实是在Realm抛出的，但是由于executeLogin（）方法抛出的异常是从login（）来的，
                 * login抛出的异常类型是AuthenticationException，所以要去获取它的子类异常才能获取到我们在Realm抛出的异常类型。
                 * */
                String msg = e.getMessage();
                Throwable cause = e.getCause();
                if (cause != null && cause instanceof TokenExpiredException) {
                    //AccessToken过期，尝试去刷新token
                    String result = refreshToken(request, response);
                    if (result.equals("success")) {
                        return true;
                    }else {
                        exceptionResponse(ResultCode.TOKEN_FAILER_CODE.getCode(), result, response);
                    }
                } else {
                    exceptionResponse(ResultCode.TOKEN_FAILER_CODE.getCode(), e.getMessage(), response);
                }
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

    private String refreshToken(ServletRequest request, ServletResponse response) {

        HttpServletRequest req = (HttpServletRequest) request;
        //获取传递过来的accessToken
        String token = req.getHeader("Authorization");
        //获取token里面的用户名
        String username = JWTUntils.getClaim(token, "username");
        //判断refreshToken是否过期了，过期了那么所含的username的键不存在
        if (RedisUtil.hasKey(Constant.SHIRO_REFRESH_TOKEN + username)) {
            //判断refresh的时间节点和传递过来的accessToken的时间节点是否一致，不一致校验失败
            Long current = Long.parseLong(RedisUtil.get(Constant.SHIRO_REFRESH_TOKEN + username));
            if (current.equals(Long.parseLong(JWTUntils.getClaim(token, Constant.CURREN_TIIME_MILLIS)))) {
                //获取当前时间节点
                long currentTimeMillis = System.currentTimeMillis();
                //生成token
                HashMap<String, String> claimMap = new HashMap<>();
                claimMap.put("username", username);
                claimMap.put(Constant.CURREN_TIIME_MILLIS, String.valueOf(currentTimeMillis));
                //响应生成请求头
                token = JWTUntils.getToken(claimMap);
                //刷新redis里面的refreshToken,过期时间是30min
                // 设置RefreshToken中的时间戳为当前最新时间戳，且刷新过期时间重新为30分钟过期(配置文件可配置refreshTokenExpireTime属性)
                RedisUtil.setEx(Constant.SHIRO_REFRESH_TOKEN + username, String.valueOf(currentTimeMillis),
                        Long.parseLong(SecurityBean.refreshTokenExpireTime), TimeUnit.SECONDS);
                //再次交给shiro进行认证
                JwtToken jwtToken = new JwtToken(token);
                try {
                    getSubject(request, response).login(jwtToken);
                    // 最后将刷新的AccessToken存放在Response的Header中的Authorization字段返回
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.setHeader("Authorization", token);
                    httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                    return "success";
                } catch (Exception e) {
                    return e.getMessage();
                }
            }
        }
        return "token认证失效，token过期，重新登陆";
    }

    private void exceptionResponse(int code, String msg, ServletResponse response) {
        //返回结果异常
        Result result = Result.fail().code(code).message(msg);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSON.toJSONString(result));
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
