package com.example.test.test;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;

/**
 * @PROJECT_NAME: Test
 * @DESCRIPTION:
 * @USER: wusiyu
 * @DATE: 2021/3/1 16:54
 */
public class asymmetric {

    /**
     * RSA 是目前最有影响力的非对称加密算法，能够抵抗到目前为止已知的所有密码攻击，已被ISO推荐为公钥数据加密标准。
     * RSA 算法是基于一个十分简单的数论事实：将两个大素数想乘十分容易，但反过来想要对其乘积进行因式分解却极其困难。
     * 　　常见的数字签名有两种：
     * 1.MD5withRSA ：采用MD5 算法生成需要发送正文的数字摘要，并且使用RSA算法来对摘要进行加密和解密。
     * 2.SHA1withRSA: 采用SHA-1算法生成正文的数字摘要，并且使用RSA算法来对摘要进行加密和解密。
     * 两者算法的流程完全一致，只是签名算法换成了对应的算法。这里只对MD5withRSA做出代码示例。
     */
    public static void main(String[] args) throws Exception{
        // 初始化KeyPairGenerator，生成KeyPair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        System.out.println("privateKey:" + getPrivateKey(keyPair));
        System.out.println("publicKey:"+getPublicKey(keyPair));
        String content = "沙雕获奖者：孟某";
        byte[] signMD5 = sgin4MD5(content.getBytes(),keyPair.getPrivate());
        System.out.println("signMd5"+new String(signMD5));
        System.out.println("verifyMd5:"+verify4MD5(content.getBytes(), signMD5, keyPair.getPublic()));
        byte[] signSignature = sign4Signature(content.getBytes(), keyPair.getPrivate());
        System.out.println("sign4Signature:"+new String(signSignature));
        System.out.println("verify4Signature:"+verify4Signature(content.getBytes(), signSignature, keyPair.getPublic()));
    }

    // 获取公钥
    public static String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        Base64 base64 = new Base64();
        String str = base64.encodeAsString(bytes);
        return str;
    }

    // 获取私钥
    public static String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        Base64 base64 = new Base64();
        String str = base64.encodeAsString(bytes);
        return str;
    }

    // MD5withRSA 签名，可将MD5替换成SHA1数字签名
    public static byte[] sgin4MD5(byte[] content,PrivateKey privateKey) throws Exception{
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = messageDigest.digest(content);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,privateKey);
        byte[] encryptBytes = cipher.doFinal(bytes);
        return encryptBytes;
    }

    // MD5withRSA 验证签名，可将MD5替换成SHA1数字签名
    public static boolean verify4MD5(byte[] content,byte[] sign,PublicKey publicKey) throws Exception{
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = messageDigest.digest(content);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,publicKey);
        byte[] decrypeBytes = cipher.doFinal(sign);
        Base64 base = new Base64();
        String bytesStr = base.encodeAsString(bytes);
        String decrypeByteStr = base.encodeAsString(decrypeBytes);
        return bytesStr.equals(decrypeByteStr);
    }

    // 基于JAVA的Signature API的使用，蒋MD5withRSA替换成SHA1withRSA数字签名
    public static byte[] sign4Signature(byte[] content,PrivateKey privateKey) throws Exception{
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey);
        signature.update(content);
        return signature.sign();
    }

    public static boolean verify4Signature(byte[] content,byte[] sign,PublicKey publicKey) throws Exception{
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey);
        signature.update(content);
        return signature.verify(sign);
    }


}
