package com.leader.ren.model.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysCoreUserRoleLink {
    private Long id;

    private Long userId;

    private Long roleId;

    private Integer status;

    private Long createBy;

    private Date createAt;

    private Long updateBy;

    private Date updateAt;

}