package com.mxy.system.entity.vo;

import com.mxy.common.core.entity.SysCountry;
import com.mxy.common.core.entity.vo.BaseVO;
import lombok.Data;

import java.util.Date;
/**
 * <p>
 * 国家省分地市区县-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2022-05-10
 */
@Data
public class SysCountryVO extends BaseVO<SysCountry> {

    /**
     * ID
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
     * 父ID
     */
    private String parentId;
    /**
     * 全路径
     */
    private String path;
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
