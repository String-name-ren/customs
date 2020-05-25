package com.leader.ren.model.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysCoreRelation {
    private Long id;

    private Long roleId;

    private Long resourceId;

    private String resourceType;

    private String description;

    private Byte status;

    private Long createBy;

    private Date createAt;

    private Long updateBy;

    private Date updateAt;

}