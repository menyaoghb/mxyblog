package com.mxy.security.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录请求实体
 * @author mengyao
 */
@Data
public class LoginRequestReq {
	
	@ApiModelProperty(value = "手机号码",name = "phoneNo")
	private String phoneNo;
	
	@ApiModelProperty(value = "手机验证码",name = "messageCode")
	private String messageCode;

}
