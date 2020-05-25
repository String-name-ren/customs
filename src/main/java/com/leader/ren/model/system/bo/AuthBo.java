package com.leader.ren.model.system.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 认证实体bo
 *
 * @author 李刚
 * date 2018/11/30 16:57
 */
@Data
@ApiModel(value = "认证用户请求实体")
public class AuthBo implements Serializable {

	private static final long serialVersionUID = -5258552132842005102L;

	/**
	 * 应用主键
	 */
	private String appKey;

	/**
	 * 用户令牌
	 */
	private String token;

	/**
	 * 店员工号
	 */
	private String saleCode;

	/**
	 * 用户账号
	 */
	private String username;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 图形验证码
	 */
	private String codeImg;

	/**
	 * 短信验证码
	 */
	private String codeSms;
	
	/**
	 * 短信发送时间
	 */
	private String sendTime;

	/**
	 * 登录系统类型（store：店铺后台）
	 */
	private String type;

	/**
	 * 商铺id
	 */
	private String shopcode;
}
