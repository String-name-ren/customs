package com.leader.ren.common.utils;

/**
 * 编码枚举
 *
 * @author maoenqi
 * @date 2018/5/27
 */
public enum CharsetEnum {
    UTF_8("UTF-8"),
    UTF_16("UTF-16"),
    GBK("GBK"),
    ISO_8859_1("ISO-8859-1");
    private String name;

    CharsetEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}