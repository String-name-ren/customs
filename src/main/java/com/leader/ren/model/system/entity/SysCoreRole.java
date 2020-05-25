package com.leader.ren.model.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysCoreRole {
    private Long id;

    private String code;

    private String name;

    private String icon;

    private String description;

    private Integer status;

    private Long createBy;

    private Date createAt;

    private Long updateBy;

    private Date updateAt;
}