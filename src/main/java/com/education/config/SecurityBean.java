package com.education.config;

import com.education.controller.upload.UploadFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-05-18-02
 */
@Component
public class SecurityBean {
    /**
     * refreshToken过期时间
     */

    public static String refreshTokenExpireTime;

    /**
     * token过期时间
     */
    public static String accessTokenExpireTime;

    /**
     * JWT认证加密盐值(Base64加密)
     */
    public static String encryptJWTKey;

    /**
     * RSA私钥(RSA私钥)
     */
    public static String encryptRSAKey;

    //文件路径
    private static String filePath;

    @Value("${security.refreshTokenExpireTime}")
    private void setRefreshTokenExpireTime(String refreshTokenExpireTime) {
        SecurityBean.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    @Value("${security.accessTokenExpireTime}")
    private  void setAccessTokenExpireTime(String accessTokenExpireTime) {
        SecurityBean.accessTokenExpireTime = accessTokenExpireTime;
    }

    /**
     * set令牌加密盐值
     */
    @Value("${security.encryptJWTKey}")
    private void setEncryptJWTKey(String encryptJWTKey) {
        SecurityBean.encryptJWTKey = encryptJWTKey;
    }

    /**
     * RSA算法私钥
     */
    @Value("${security.encryptRSAKey}")
    public void setEncryptRSAKey(String encryptRSAKey) {
        SecurityBean.encryptRSAKey = encryptRSAKey;
    }


    /**
     * 文件路径
     */
    @Value("${education.uploadFile.filePath}")
    public void setFilePath(String filePath){
        SecurityBean.filePath = filePath;
    }
}
