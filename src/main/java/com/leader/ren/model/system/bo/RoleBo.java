package com.leader.ren.model.system.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "角色请求实体")
public class RoleBo implements Serializable {

    private static final long serialVersionUID = -5258552132842005108L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status = 1;

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
     * 用户主键
     */
    private Long selUid;

    /**
     * 标记（是否被当前用户选择）
     */
    private Boolean mark;

    /**
     * 标记（是否被当前用户选择）
     */
    private Long roleId;

    /**
     * 标记
     */
    private Boolean flag;

    private String filterText;
}
