package com.leader.ren.model.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysCoreUser {
    private Long id;

    private String username;

    private String password;

    private String name;

    private String icon;

    private String phone;

    private String email;

    private String openId;

    private String description;

    private Integer status;

    private Long createBy;

    private Date createAt;

    private Long updateBy;

    private Date updateAt;

    private String permission;
}