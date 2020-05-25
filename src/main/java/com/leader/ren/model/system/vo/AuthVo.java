package com.leader.ren.model.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 认证实体vo
 *
 * @author 李刚
 * date 2018/11/30 16:57
 */
@Data
@ApiModel(value = "认证用户响应实体")
public class AuthVo implements Serializable {

	private static final long serialVersionUID = -5258552132842005202L;


	@ApiModelProperty(value="用户令牌")
	private String token;


	@ApiModelProperty(value="用户信息")
	private UserVo user;


	@ApiModelProperty(value="图形验证码")
	private String codeImg;

	@ApiModelProperty(value="短信验证码")
	private String codeSms;

	@ApiModelProperty(value="用户菜单")
	private List<MenuVo> menus;


	@ApiModelProperty(value="用户元素")
	private List<ElementVo> elements;


	@ApiModelProperty(value="用户角色")
	private List<RoleVo> roles;
}
