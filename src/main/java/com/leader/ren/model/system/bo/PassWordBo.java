package com.leader.ren.model.system.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 密码请求实体bo
 *
 * @author 李刚
 * date 2018/11/30 16:57
 */
@Data
@ApiModel(value = "修改密码请求实体")
public class PassWordBo implements Serializable {

	private static final long serialVersionUID = -5258552132842005102L;

	/**
	 * 用户登录名
	 */
	@ApiModelProperty(value = "登录名称", required = true)
	private String username;

	/**
	 * 原密码
	 */
	@ApiModelProperty(value = "原密码", required = true)
	private String passWord;

	/**
	 * 新密码
	 */
	@ApiModelProperty(value = "新密码", required = true)
	private String newPassWord;
}
