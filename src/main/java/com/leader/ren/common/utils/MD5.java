package com.leader.ren.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * MD5签名处理核心文件
 */
public class MD5 {

    // 默认编码类型
    public static String charset = "UTF-8";

    /**
     * 签名字符串
     *
     * @param text
     *            要签名的字符串
     *
     * @return 签名结果
     */
    public static String sign(String text) {
        return DigestUtils.md5Hex(getContentBytes(text, charset));
    }

    /**
     * 签名字符串
     *
     * @param text
     *            要签名的字符串
     * @param key
     *            密钥
     *
     * @return 签名结果
     */
    public static String sign(String text, String key) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, charset));
    }

    /**
     * 签名字符串
     *
     * @param text
     *            要签名的字符串
     * @param sign
     *            签名结果
     * @param key
     *            密钥
     *
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, charset));
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 签名字符串
     *
     * @param text
     *            要签名的字符串
     * @param key
     *            密钥
     * @param charset
     *            编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String charset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, charset));
    }

    /**
     * 签名字符串
     *
     * @param text
     *            要签名的字符串
     * @param sign
     *            签名结果
     * @param key
     *            密钥
     * @param charset
     *            编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String charset) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, charset));
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param content
     * @param charset
     * @return byte[]
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出错,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 拼接参数字符串
     *
     * @param datas
     * @return
     */
    public static String signMap(Map<String, String> datas, String key) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>(datas);

        StringBuilder strBuilder = new StringBuilder();

        Iterator<Map.Entry<String, String>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entrySet = it.next();
            if(entrySet.getKey().startsWith("_")){
                continue;
            }
            strBuilder.append(entrySet.getKey()).append('=').append(entrySet.getValue()).append('&');
        }

        String signString = strBuilder.substring(0, strBuilder.length() - 1);

        return sign(signString, key);
    }

}