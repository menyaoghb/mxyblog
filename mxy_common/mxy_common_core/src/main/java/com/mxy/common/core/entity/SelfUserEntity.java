package com.mxy.common.core.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * SpringSecurity用户的实体
 * 注意:这里必须要实现UserDetails接口
 * @Author Mxy
 * @CreateTime 2022/01/1 20:23
 */
@Data
public class SelfUserEntity implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 状态:0正常  1禁用
	 */
	private String status;
	/**
	 * 头像地址
	 */
	private String avatar;
	/**
	 * 真实姓名
	 */
	private String relName;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 用户类型（00系统用户）
	 */
	private String userType;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 手机号码
	 */
	private String phoneNumber;
	/**
	 * 用户性别（0男 1女 2未知）
	 */
	private String sex;
	/**
	 * 注册类型
	 */
	private String registrationType;
	/**
	 * 平台UUID
	 */
	private String uuid;
	/**
	 * 登录次数
	 */
	private Integer loginCount;
	/**
	 * 操作系统
	 */
	private String os;
	/**
	 * 浏览器
	 */
	private String browser;
	/**
	 * ip来源
	 */
	private String ipSource;
	/**
	 * 验证码
	 */
	private String validCode;
	/**
	 * 是否删除（0未删除 1已删除）
	 */
	private String isDelete;
	/**
	 * 最后登录IP
	 */
	private String loginIp;
	/**
	 * 最后登录时间
	 */
	private Date loginDate;
	/**
	 * 创建者
	 */
	private String createUser;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新者
	 */
	private String updateUser;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;



	/**
	 * 用户角色
	 */
	private Collection<GrantedAuthority> authorities;
	/**
	 * 账户是否过期
	 */
	private boolean isAccountNonExpired = false;
	/**
	 * 账户是否被锁定
	 */
	private boolean isAccountNonLocked = false;
	/**
	 * 证书是否过期
	 */
	private boolean isCredentialsNonExpired = false;
	/**
	 * 账户是否有效
	 */
	private boolean isEnabled = true;


	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}