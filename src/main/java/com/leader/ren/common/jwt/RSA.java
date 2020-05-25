package com.leader.ren.common.jwt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * <p>RSA签名,加解密处理核心文件，注意：密钥长度1024</p>
 *
 */
public class RSA {
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM       = "RSA";

    /**
     * 密钥长度1024
     */
    public static final int    KEY_LENGTH          = 1024;

    /**
     * RSA最大加密明文大小
     */
    private static final int   MAX_ENCRYPT_BLOCK   = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int   MAX_DECRYPT_BLOCK   = 128;

    /**
     * 获取公钥的key
     */
    public static final String PUBLIC_KEY          = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    public static final String PRIVATE_KEY         = "RSAPrivateKey";

    /**
     * 默认编码类型
     */
    public static final String DEFAULT_CHARSET     = "UTF-8";

    /**
     * <p>生成密钥（公钥和私钥）</p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> makeKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_LENGTH);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * <p>生成密钥（生成Base64编码的公钥和私钥）</p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, String> makeStrKeyPair() throws Exception {
        Map<String, Object> keyMap = makeKeyPair();

        Map<String, String> keyStrMap = new HashMap<String, String>();

        keyStrMap.put(PUBLIC_KEY, getKeyString((Key) keyMap.get(PUBLIC_KEY)));
        keyStrMap.put(PRIVATE_KEY, getKeyString((Key) keyMap.get(PRIVATE_KEY)));

        return keyStrMap;
    }

    /**
     * 创建私钥对象
     *
     * @param privateKey
     *          私钥（Base64编码）
     * @return
     * @throws Exception
     */
    public static PrivateKey buildPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(keySpec);

        return privateK;
    }

    /**
     * 创建公钥对象
     *
     * @param publicKey
     *          公钥（Base64编码）
     * @return
     * @throws Exception
     */
    public static PublicKey buildPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);

        return publicK;
    }

    /**
     * 签名字符串
     *
     * @param text
     *          要签名的字符串
     * @param privateKey
     *          私钥（Base64编码）
     *
     * @return 签名结果（Base64编码）
     */
    public static String sign(String text, String privateKey) throws Exception {
        return sign(text, privateKey, DEFAULT_CHARSET);
    }

    /**
     * 签名字符串
     *
     * @param text
     *            要签名的字符串
     * @param sign
     *            签名结果（Base64编码）
     * @param publicKey
     *            公钥（Base64编码）
     *
     * @return 验签结果
     */
    public static boolean verify(String text, String sign, String publicKey) throws Exception {
        return verify(text, sign, publicKey, DEFAULT_CHARSET);
    }

    /**
     * 签名字符串
     *
     * @param text
     *          要签名的字符串
     * @param privateKey
     *          私钥（Base64编码）
     * @param charset
     *          编码格式
     * @return 签名结果（Base64编码）
     */
    public static String sign(String text, String privateKey, String charset) throws Exception {
        PrivateKey privateK = buildPrivateKey(privateKey);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(getContentBytes(text, charset));
        byte[] result = signature.sign();
        return Base64.encodeBase64String(result);

    }

    /**
     * 验证签名字符串
     *
     * @param text
     *          要签名的字符串
     * @param sign
     *          签名结果（Base64编码）
     * @param publicKey
     *          公钥（Base64编码）
     * @param charset
     *          编码格式
     * @return 验签结果
     */
    public static boolean verify(String text, String sign, String publicKey, String charset) throws Exception {
        PublicKey publicK = buildPublicKey(publicKey);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(getContentBytes(text, charset));
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * <p>公钥加密</p>
     *
     * @param data
     *          源数据
     * @param publicKey
     *          公钥（Base64编码）
     * @return byte[]
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        // 公钥
        Key publicK = buildPublicKey(publicKey);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(publicK.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;

    }

    /**
     * <p>私钥加密</p>
     *
     * @param data
     *          源数据
     * @param privateKey
     *          私钥（Base64编码）
     * @return byte[]
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        // 私钥
        Key privateK = buildPrivateKey(privateKey);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(privateK.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;

    }

    /**
     * <p>公钥解密</p>
     *
     * @param encryptedData
     *          已加密数据
     * @param publicKey
     *          公钥（Base64编码）
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        // 公钥
        Key publicK = buildPublicKey(publicKey);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(publicK.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;

    }

    /**
     * <P>私钥解密</p>
     *
     * @param encryptedData
     *          已加密数据
     * @param privateKey
     *          私钥（Base64编码）
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        // 私钥
        Key privateK = buildPrivateKey(privateKey);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(privateK.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;

    }

    /**
     * @param key
     *          密钥
     *
     * @return 得到密钥字符串（经过Base64编码）
     */
    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String keyString = Base64.encodeBase64String(keyBytes);
        return keyString;
    }

    /**
     * @param content
     *          内容
     * @param charset
     *          编码
     * @return byte[]
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名出错,指定的编码集(" + charset + ")不正确!");
        }
    }
}