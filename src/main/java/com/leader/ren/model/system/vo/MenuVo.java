package com.leader.ren.model.system.vo;

import com.leader.ren.common.dto.TreeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单Vo
 *
 * @author 王泽志
 * date 2018/11/30 16:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("菜单响应实体")
public class MenuVo extends TreeVo implements Serializable {

    private static final long serialVersionUID = -5258552132842005206L;


    @ApiModelProperty(value="主键")
    private Long id;


    @ApiModelProperty(value="父级主键")
    private Long parentId;

    @ApiModelProperty(value="编码")
    private String code;


    @ApiModelProperty(value="名称")
    private String name;


    @ApiModelProperty(value="路径")
    private String path;


    @ApiModelProperty(value="链接")
    private String href;


    @ApiModelProperty(value="图标")
    private String icon;


    @ApiModelProperty(value="顺序")
    private Integer order;


    @ApiModelProperty(value="描述")
    private String description;


    @ApiModelProperty(value="状态")
    private Integer status = 1;


    @ApiModelProperty(value="创建人员")
    private Long createBy;


    @ApiModelProperty(value="创建时间")
    private Date createAt;


    @ApiModelProperty(value="更新人员")
    private Long updateBy;


    @ApiModelProperty(value="更新时间")
    private Date updateAt;
}
