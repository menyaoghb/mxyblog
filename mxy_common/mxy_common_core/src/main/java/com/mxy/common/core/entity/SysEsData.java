package com.mxy.common.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mxy.common.core.entity.vo.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * ES数据存储
 * </p>
 *
 * @author 孟小耀
 * @since 2022-05-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("sys_es_data")
@ApiModel(value="SysEsData对象", description="ES数据存储")
public class SysEsData extends BaseEntity<SysEsData> {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 唯一标识
     */
    private String dataId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private Long phone;

    /**
     * 工资
     */
    private BigDecimal salary;

    /**
     * 公司
     */
    private String company;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 地址
     */
    private String address;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 是否删除
     */
    @TableLogic
    private String isDelete;

    /**
     * 备用字段一
     */
    private String fieldOne;

    /**
     * 备用字段二
     */
    private String fieldTwo;

    /**
     * 备用字段三
     */
    private String fieldThree;

    /**
     * 备用字段四
     */
    private String fieldFour;

    /**
     * 备用字段五
     */
    private String fieldFive;

    /**
     * 备用字段六
     */
    private String fieldSix;

    /**
     * 备用字段七
     */
    private String fieldSeven;

    /**
     * 备用字段八
     */
    private String fieldEight;

    /**
     * 备用字段九
     */
    private Integer fieldNine;

    /**
     * 备用字段十
     */
    private Date fieldTen;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
