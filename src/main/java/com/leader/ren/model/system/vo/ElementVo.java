package com.leader.ren.model.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "元素响应实体")
public class ElementVo implements Serializable {

	private static final long serialVersionUID = -5258552132842005204L;


	@ApiModelProperty(value="主键")
	private Long id;


	@ApiModelProperty(value="父级主键")
	private Long parentId;


	@ApiModelProperty(value="所属菜单主键")
	private Long menuId;


	@ApiModelProperty(value="类型")
	private String type;


	@ApiModelProperty(value="编码")
	private String code;


	@ApiModelProperty(value="名称")
	private String name;


	@ApiModelProperty(value="图标")
	private String icon;


	@ApiModelProperty(value="链接")
	private String href;


	@ApiModelProperty(value="路径")
	private String path;


	@ApiModelProperty(value="方法")
	private String method;


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


	@ApiModelProperty(value="是否关联(0未关联，1已关联)")
	private Integer isRelation = 0;


	@ApiModelProperty(value="前台是否勾选标记 （true 勾选 false 未勾选）")
	private Boolean flag;

}
