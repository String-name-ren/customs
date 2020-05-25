package com.leader.ren.model.system.enums;

public enum StatusEnum {

    NORMAL(1, "正常"),
    DISABLED(0, "失效"),
    ;

    private int code;
    private String name;

    StatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
