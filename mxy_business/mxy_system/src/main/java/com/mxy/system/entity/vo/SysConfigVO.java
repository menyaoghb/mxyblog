package com.mxy.system.entity.vo;

import com.mxy.common.core.entity.SysConfig;
import com.mxy.common.core.entity.vo.BaseVO;
import lombok.Data;

import java.util.Date;
/**
 * <p>
 * 系统参数配置-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2022-02-10
 */
@Data

public class SysConfigVO extends BaseVO<SysConfig> {

    /**
     * 主键
     */
    private String sysId;
    /**
     * 参数名称
     */
    private String configName;
    /**
     * 参数值
     */
    private String configValue;
    /**
     * 参数类型
     */
    private String configType;
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    /**
     * 是否删除（0未删除1已删除）
     */
    private String isDelete;
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

}
