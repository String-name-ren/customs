package com.leader.ren.model.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-08-27 17:39
 **/
@Data
public class RoleVo {

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

    @ApiModelProperty(value="前台是否勾选标记")
    private Boolean mark;
}
