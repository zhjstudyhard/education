package com.education.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.config.SecurityBean;
import com.education.exceptionhandler.EducationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-02-02-17-20
 */
@Component
public class JWTUntils {



    //创建token
    public static String getToken(Map<String, String> map) {
        //创建jwt生成器
        JWTCreator.Builder builder = JWT.create();
        for (String key : map.keySet()) {
            builder.withClaim(key, map.get(key));
        }
        String token = builder.withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(SecurityBean.accessTokenExpireTime) * 1000))
                .sign(Algorithm.HMAC256(SecurityBean.encryptJWTKey)).toString();
        return token;
    }

    //解析验证token
    public static boolean verify(String token) {
        //创建token解析器
        JWTVerifier build = JWT.require(Algorithm.HMAC256(SecurityBean.encryptJWTKey)).build();
        try {
            DecodedJWT verify = build.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //    //获取用户名
//    public static String getUserName(String token){
//        DecodedJWT decode = JWT.decode(token);
//        String username = decode.getClaim("username").asString();
//        return username;
//    }
    //获取载荷数据
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT decode = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return decode.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }
}
