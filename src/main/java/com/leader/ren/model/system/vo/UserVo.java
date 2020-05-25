package com.leader.ren.model.system.vo;

import lombok.Data;

import java.util.Date;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-08-27 17:02
 **/
@Data
public class UserVo {
    private Long id;

    private String username;

    private String password;

    private String name;

    private String icon;

    private String phone;

    private String email;

    private String description;

    private Integer status;

    private Date createAt;

    private Date updateAt;

    private Integer admin;
}
