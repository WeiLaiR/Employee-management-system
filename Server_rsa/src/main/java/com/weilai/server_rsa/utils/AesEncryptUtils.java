package com.weilai.server_rsa.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;


public class AesEncryptUtils {
    //可配置到Constant中，并读取配置文件注入,16位,自己定义
    private static final String KEY = "HTTP0BAIBLOG0TOP";

    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String INFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * 加密数据需要携带key
     * @param content 加密的字符串
     * @param encryptKey key值
     */
    public static String encrypt(String content, String encryptKey) throws Exception {
        KeyGenerator kGen = KeyGenerator.getInstance("AES");
        kGen.init(128);
        Cipher cipher = Cipher.getInstance(INFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        byte[] b = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        // 采用base64算法进行转码,避免出现中文乱码
        return Base64.encodeBase64String(b);

    }

    /**
     * 解密数据需要携带key
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     */
    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
        KeyGenerator kGen = KeyGenerator.getInstance("AES");
        kGen.init(128);
        Cipher cipher = Cipher.getInstance(INFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        // 采用base64算法进行转码,避免出现中文乱码
        byte[] encryptBytes = Base64.decodeBase64(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /**
     * 加密数据
     * @param content 加密的字符串
     * @return 加密的数据
     * @throws Exception .
     */
    public static String encrypt(String content) throws Exception {
        return encrypt(content, KEY);
    }

    /**
     * 解密数据
     * @param encryptStr 解密的字符串
     * @return 解密的数据
     * @throws Exception .
     */
    public static String decrypt(String encryptStr) throws Exception {
        return decrypt(encryptStr, KEY);
    }
}
