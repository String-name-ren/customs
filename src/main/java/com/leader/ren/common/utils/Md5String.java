package com.leader.ren.common.utils;

import java.security.MessageDigest;

/**
 * MD5 加密解密
 * @author: 吴跃
 * date: 2019/4/15 14:59
 **/
public class Md5String {
    public static final String Secret_MD5 = "MD5";
    public static final String Secret_SHA1 = "SHA-1";
    private MessageDigest m_md;
    private String secret_key;

    public Md5String()
    {
        this("MD5");
    }

    public Md5String(String secretType)
    {
        try
        {
            this.m_md = MessageDigest.getInstance(secretType);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String md5(String str)
    {
        Md5String s = new Md5String("MD5");
        s.addNormalObject(str);
        return s.toString();
    }

    public static String sha1(String str)
    {
        Md5String s = new Md5String("SHA-1");
        s.addNormalObject(str);
        return s.toString();
    }

    public static String md5(byte[] b)
    {
        Md5String s = new Md5String("MD5");
        s.addNormalBytes(b);
        return s.toString();
    }

    public static String sha1(byte[] b)
    {
        Md5String s = new Md5String("SHA-1");
        s.addNormalBytes(b);
        return s.toString();
    }

    public Md5String setSecretKey(String secretKey)
    {
        this.secret_key = secretKey;
        return this;
    }

    public void reset()
    {
        this.m_md.reset();
    }

    public Md5String addSecretBytes(byte[] b)
    {
        if ((b != null) && (b.length > 0)) {
            this.m_md.update(b);
        }
        return this;
    }

    public Md5String addSecretString(String secr)
    {
        if (secr != null) {
            this.m_md.update(((this.secret_key == null ? "|" : new StringBuilder(String.valueOf(this.secret_key)).append("|").toString()) + secr).toLowerCase().getBytes());
        } else {
            this.m_md.update((this.secret_key + "|").getBytes());
        }
        return this;
    }

    public Md5String addSecret(Object secr)
    {
        addSecretString(secr != null ? secr.toString() : null);
        return this;
    }

    public Md5String addSecretInt(int secr)
    {
        addSecretString(String.valueOf(secr));
        return this;
    }

    public Md5String addSecretLong(long secr)
    {
        addSecretString(String.valueOf(secr));
        return this;
    }

    public Md5String addNormalObject(Object secr)
    {
        if ((secr != null) && (secr.toString() != null)) {
            this.m_md.update(secr.toString().getBytes());
        }
        return this;
    }

    public Md5String addNormalInt(int secr)
    {
        this.m_md.update(String.valueOf(secr).getBytes());
        return this;
    }

    public Md5String addNormalLong(long secr)
    {
        this.m_md.update(String.valueOf(secr).getBytes());
        return this;
    }

    public Md5String addNormalBytes(byte[] b)
    {
        this.m_md.update(b);
        return this;
    }

    @Override
    public String toString()
    {
        return md5HexString();
    }


    private String md5HexString()
    {
        byte[] key = this.m_md.digest();

        StringBuilder strbuf = new StringBuilder();
        String temp = "";
        for (int i = 0; i < key.length; i++)
        {
            temp = Integer.toHexString(0xFF & key[i]).toUpperCase();
            if (temp.length() == 1) {
                strbuf.append("0");
            }
            strbuf.append(temp.toUpperCase());
        }
        return strbuf.toString();
    }

}
