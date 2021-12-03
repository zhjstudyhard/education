package com.education.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-02-02-17-20
 */
public class JWTUntils {

    /**
     * JWT认证加密盐值(Base64加密)
     */
    private static String encryptJWTKey;

    /**
     * token过期时间
     */
    private static String accessTokenExpireTime;

    /**
     * set令牌加密盐值
     */
    @Value("${security.encryptJWTKey}")
    private void setEncryptJWTKey(String encryptJWTKey){
       JWTUntils.encryptJWTKey = encryptJWTKey;
    }
    /**
     * set令牌过期时间
     */
    @Value("${security.accessTokenExpireTime}")
    private void setAccessTokenExpireTime(String accessTokenExpireTime){
        JWTUntils.accessTokenExpireTime = accessTokenExpireTime;
    }


    //创建token
    public static String getToken(Map<String,String> map){
        //创建jwt生成器
        JWTCreator.Builder builder = JWT.create();
        for(String key : map.keySet()){
            builder.withClaim(key,map.get(key));
        }
        String token = builder.withExpiresAt(new Date(Long.parseLong(accessTokenExpireTime)*1000))
                .sign(Algorithm.HMAC256(encryptJWTKey)).toString();
        return token;
    }

    //解析验证token
    public static boolean verify(String token) {
         //创建token解析器
        JWTVerifier build = JWT.require(Algorithm.HMAC256(encryptJWTKey)).build();
        try {
            DecodedJWT verify = build.verify(token);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //获取用户名
    public static String getUserName(String token){
        DecodedJWT decode = JWT.decode(token);
        String username = decode.getClaim("username").asString();
        return username;
    }
    //获取用户名id
    public static String getUserId(String token){
        DecodedJWT decode = JWT.decode(token);
        String userId = decode.getClaim("userId").asString();
        return userId;
    }
}
