package com.education.util;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-10-18-00
 */
public class RSAUtil {

    //生成RSA密钥对
    public static Map<Integer, String> genKeyPair() throws Exception {
        HashMap<Integer, String> keyMap = new HashMap<>();
        Base64.Encoder encoder = Base64.getEncoder();
        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
        rsa.initialize(1024, new SecureRandom());
        //生成密钥对
        KeyPair keyPair = rsa.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = new String(encoder.encode(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(encoder.encode(privateKey.getEncoded()));
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString);  //0表示公钥
        keyMap.put(1, privateKeyString);  //1表示私钥
        return keyMap;
    }

    //RSA加密
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        Base64.Decoder decoder = Base64.getDecoder();
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] decoded = decoder.decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = encoder.encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }
    //RSA解密
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        Base64.Decoder decoder = Base64.getDecoder();
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] inputByte = decoder.decode(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = decoder.decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
