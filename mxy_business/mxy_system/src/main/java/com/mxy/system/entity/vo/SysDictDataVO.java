package com.mxy.system.entity.vo;

import java.util.Date;
import lombok.Data;
import com.mxy.common.core.entity.vo.BaseVO;
import com.mxy.common.core.entity.SysDictData;

/**
 * <p>
 * 字典数据-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Data
public class SysDictDataVO extends BaseVO<SysDictData> {

    /**
     * 字典主键
     */
    private String dictCode;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典编码
     */
    private String dictValue;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 字典排序
     */
    private Integer dictSort;
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
