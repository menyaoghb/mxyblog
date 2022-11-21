package com.mxy.system.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.mxy.common.core.entity.vo.BaseVO;
import com.mxy.common.core.entity.SysUser;

import java.util.Date;
/**
 * <p>
 * 用户信息-VO
 * </p>
 *
 * @author mengyao
 * @since 2022-11-21
 */
@Data
public class SysUserVO extends BaseVO<SysUser> {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
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
     * 头像地址
     */
    private String avatar;
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
     * 帐号状态（0正常 1停用）
     */
    private String status;
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
     * 旧密码
     */
    private String oldPassword;
}
