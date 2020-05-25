package com.leader.ren.model.system.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "元素请求实体")
public class ElementBo implements Serializable {

	private static final long serialVersionUID = -5258552132842005104L;

	/**
	 * 筛选文本
	 */
	private String filterText;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 父级主键
	 */
	private Long parentId;

	/**
	 * 所属菜单主键
	 */
	private Long menuId;

	/**
	 * 类型
	 */
	private String type;

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
	 * 链接
	 */
	private String href;

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 方法
	 */
	private String method;

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
}
