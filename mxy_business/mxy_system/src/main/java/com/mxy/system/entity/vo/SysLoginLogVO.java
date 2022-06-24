package com.mxy.system.entity.vo;

import com.mxy.common.core.entity.SysLoginLog;
import com.mxy.common.core.entity.vo.BaseVO;
import lombok.Data;

import java.util.Date;
/**
 * <p>
 * 登录日志-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2022-06-24
 */
@Data
public class SysLoginLogVO extends BaseVO<SysLoginLog> {

    /**
     * 登录主键
     */
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

}
