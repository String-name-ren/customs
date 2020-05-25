package com.leader.ren.common.constant;


public enum RestMsg{

    SUCCESS("S0000", "操作成功"),

    ERROR("E0000", "系统异常"),

    FAIL("S9999", "操作失败"),

    NOT_LOGIN("S0001", "未登录"),
    LOGIN_FAIL("S0002", "登录失败"),
    AUTH_CODE_ERROR("S0002", "验证码错误！"),
    NOT_AUTH("S0003", "访问受限"),
    USERNAME_OR_PASSWORD_ERROR("S0004", "账号或者密码错误"),
    PARAM_IS_NULL("S0005", "参数为空"),
    IS_EXIST("S0006", "数据已存在"),
    NOT_FOUND_RESULT("S0007", "没有找到记录"),
    PARAM_IS_TO_BIG("S0008", "参数过大,请保持在规定范围"),
    SAVE_FAIL("S0009", "保存失败"),
    MODIFY_FAIL("S0010", "修改失败"),
    SAVE_OR_MODIFY_FAIL("S0011", "保存或修改失败"),
    DELETE_FAIL("S0012", "删除失败"),
    INVALID_DATA("S0013", "无效数据"),
    ALREADY_EXPIRATION("S0014", "已经过期"),
    NOT_PARAM("S0015", "缺少参数"),
    CHECK_CONFIG("S0016", "请检查配置"),


    IMAGE_GENERATE_ERROR("F10001", "图片验证码生成失败"),
    ;

    private String code;
    private String name;

    RestMsg(String code, String text) {
        this.code = code;
        this.name = text;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
