package com.mxy.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.mxy.common.core.entity.vo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典数据
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
@ApiModel(value="SysDictData对象", description="字典数据")
public class SysDictData extends BaseEntity<SysDictData> {

    private static final long serialVersionUID=1L;

    /**
     * 字典主键
     */
      @TableId(value = "dict_code", type = IdType.ID_WORKER_STR)
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
    @TableLogic
    private String isDelete;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.dictCode;
    }

}
