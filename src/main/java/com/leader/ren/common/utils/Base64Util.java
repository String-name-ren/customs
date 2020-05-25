package com.leader.ren.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * base64处理工具类
 */
public class Base64Util {

    // 默认编码类型
    public static String UTF_8 = "UTF-8";

    /**
     * base64编码
     * @param param
     * @return
     */
    public static String encodeBase64String(String param){
        if(StringUtils.isBlank(param)) return null;
        try {
            return Base64.encodeBase64String(param.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * base64解码
     * @param param
     * @return
     */
    public static String decodeBase64String(String param){
        if(StringUtils.isBlank(param)) return null;
        try {
            return new String(Base64.decodeBase64(param),UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}