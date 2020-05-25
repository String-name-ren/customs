package com.leader.ren.model.system.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-08-28 11:06
 **/
@Data
public class UserBo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 名称
     */
    @ApiModelProperty(value = "登录名称")
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 电话
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述信息")
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建人员
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新人员
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value="搜索的关键字")
    private String filterText;
    private Integer admin;

}
