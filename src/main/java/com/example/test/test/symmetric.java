package com.example.test.test;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @PROJECT_NAME: Test
 * @DESCRIPTION:
 * @USER: wusiyu
 * @DATE: 2021/3/1 15:26
 */
public class symmetric {

    public static void main(String[] args) throws Exception {
        /**
         * MD5 即 Message Digest Algorithm5 (信息摘要算法5),一种数字摘要的实现，摘要长度为128位，
         *  由MD4,MD3,MD2改进而来，主要增强了算法复杂度和不可逆性。
         */
        String str = md5("hello,world!");

        /**
         * SHA 全称是Secure Hash Algorithm（安全散列算法）,1995年又发布了一个修订版FIPS PUB 180-1 通常称之为SHA-1，
         *  是基于MD4算法。是现在公认的最安全的散列算法之一，被广泛使用。
         * 比较MD5: SHA-1算法生成的摘要信息长度为160位，由于摘要信息更长，运算过程更加复杂，相同的硬件上，SHA-1比 MD5更慢，但是更为安全。
         */
        String sha = sha("hello,world!");

        /**
         * DES算法属于对称加密算法，明文按64位进行分组，密钥长64位，事实上只有56位参与DES运算（第8,16,24,32,40,48,56,64位是校验位，
         *  使得每个密钥都有奇数个1)，分组后的明文和56位的密钥按位替代或交换的方法形成密文。
         * 3DES： 是DES向AES过渡的加密算法，使用3条56位的密钥对数据进行3次加密。
         */
        String des = sha("hello,world!");
        SecretKey secretDESKey = loadKeyDES();
        byte[] encryptBytes = encryptDES(des.getBytes(), secretDESKey);
        System.out.println("DES加密后:" + new String(encryptBytes));
        byte[] decryptBytes = decryptDES(encryptBytes, secretDESKey);
        System.out.println("DES解密后:" + new String(decryptBytes));

        /**
         * AES算法 全称是Advanced Encryption Standard，即高级加密标准，又称为Rijndael加密算法，是美国联邦政府采用的
         *      一种对称加密标准，这个标准用来替代原先的DES算法，目前已成为对称加密算法中最流行的算法之一。
         * 比较DES算法，AES算法作为新一代的数据加密标准，汇聚了强安全性、高性能、高效率、易用和灵活等优点，
         *      设计有三个密钥长度（128，192，256位），比DES算法加密强度更高，更加安全。
         */
        String aes = "沙雕获奖者：孟某";
        SecretKey secretAESKey = loadKeyAES();
        byte[] encryptAESBytes = encryptAES(aes.getBytes(),secretAESKey);
        System.out.println("AES加密后:"+new String(encryptAESBytes));
        byte[] decryptAESBytes = decryptAES(encryptAESBytes,secretAESKey);
        System.out.println("AES解密后:"+new String(decryptAESBytes));
    }

    public static String md5(String content) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] utf8 = content.getBytes("utf-8");
        byte[] bytes = md.digest(utf8);
        String md5Str = new BigInteger(1, bytes).toString(16);//16进制编码后
        return md5Str;
    }

    public static String sha(String content) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] utf8 = content.getBytes("utf-8");
        byte[] bytes = md.digest(utf8);
        String shaStr = new BigInteger(1, bytes).toString(16);
        return shaStr;
    }

    public static SecretKey loadKeyDES() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        SecretKey secretKey = keyGenerator.generateKey();
        Base64 base64 = new Base64();
        String base64Key = base64.encodeAsString(secretKey.getEncoded());
        Base64 baseDecode = new Base64();
        byte[] bytes = baseDecode.decode(base64Key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, "DES");
        return secretKeySpec;
    }

    public static byte[] encryptDES(byte[] source, SecretKey secretDESKey) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretDESKey);
        byte[] bytes = cipher.doFinal(source);
        return bytes;
    }

    public static byte[] decryptDES(byte[] source, SecretKey secretDESKey) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secretDESKey);
        // 真正开始解密操作
        byte[] bytes = cipher.doFinal(source);
        return bytes;
    }

    public static SecretKey loadKeyAES() throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretDESKey = keyGenerator.generateKey();
        byte[] byteKey = secretDESKey.getEncoded();
        Base64 base = new Base64();
        String base64Key = base.encodeAsString(byteKey);
        Base64 base64 = new Base64();
        byte[] bytes = base64.decode(base64Key);
        secretDESKey = new SecretKeySpec(bytes, "AES");
        return secretDESKey;
    }

    public static byte[] encryptAES(byte[] source,SecretKey secretAESKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,secretAESKey);
        byte[] bytes = cipher.doFinal(source);
        return bytes;
    }

    public static byte[] decryptAES(byte[] source,SecretKey secretAESKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,secretAESKey);
        byte[] bytes = cipher.doFinal(source);
        return bytes;
    }
}
