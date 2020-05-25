package com.leader.ren.model.system.bo;

import lombok.Data;

import java.util.Date;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-08-27 17:02
 **/
@Data
public class SysCoreUserBo {
    private Long id;

    private String username;

    private String password;

    private String name;

    private String icon;

    private String phone;

    private String email;

    private String description;

    private Byte status;

    private Long createBy;

    private Date createAt;

    private Long updateBy;

    private Date updateAt;
}
