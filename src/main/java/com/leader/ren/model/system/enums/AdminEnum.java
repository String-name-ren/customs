package com.leader.ren.model.system.enums;

public enum AdminEnum {

    ADMIN(1, "管理员"),
    NOT_ADMIN(0, "非管理员"),
    ;

    private int code;
    private String name;

    AdminEnum(int code, String name) {
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
