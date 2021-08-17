package com.mxy.system.entity.vo;

import com.mxy.common.core.entity.SysWebConfig;
import lombok.Data;
import com.mxy.common.core.entity.vo.BaseVO;

import java.util.Date;
/**
 * <p>
 * 网站参数配置-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-17
 */
@Data
public class SysWebConfigVO extends BaseVO<SysWebConfig> {

    /**
     * 主键
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private String value;
    /**
     * 标识
     */
    private String sign;
    /**
     * 类型
     */
    private String type;
    /**
     * 状态（0已发布 1未发布）
     */
    private String status;
    /**
     * 是否删除（0未删除 1已删除）
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
