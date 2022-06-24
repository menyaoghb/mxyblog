package com.mxy.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.mxy.common.core.entity.vo.BaseEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 登录日志
 * </p>
 *
 * @author 孟小耀
 * @since 2022-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_login_log")
@ApiModel(value="SysLoginLog对象", description="登录日志")
public class SysLoginLog extends BaseEntity<SysLoginLog> {

    private static final long serialVersionUID=1L;

    /**
     * 登录主键
     */
    @TableId(value = "login_id", type = IdType.ID_WORKER_STR)
    private String loginId;

    /**
     * 登录账号
     */
    private String userId;

    /**
     * 登录姓名
     */
    private String userName;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 登录地点
     */
    private String address;

    /**
     * 登录描述
     */
    private String description;

    /**
     * 登录方式
     */
    private String loginType;

    /**
     * 登录时间
     */
    private Date loginTime;


    @Override
    protected Serializable pkVal() {
        return this.loginId;
    }

}
