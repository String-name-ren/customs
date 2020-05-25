package com.leader.ren.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class IOUtil {

    public static InputStream byte2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }


    /**
     * 功能描述:
     *
     * @param inputStream 输入流
     * @return byte[] 数组
     * @author xiaobu
     * @date 2019/3/28 16:03
     * @version 1.0
     */
    public static byte[] inputStream2byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }


    public static void main(String[] args) {
        String str = "你好呀";
        byte[] bytes = str.getBytes();
        InputStream inputStream = byte2InputStream(bytes);
        try {
            byte[] bytes1 = inputStream2byte(inputStream);
            String string = new String(bytes1, StandardCharsets.UTF_8);
            System.out.println("string = " + string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
