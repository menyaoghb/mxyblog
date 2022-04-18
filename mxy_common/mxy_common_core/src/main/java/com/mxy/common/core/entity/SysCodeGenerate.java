package com.mxy.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mxy.common.core.entity.vo.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 代码生成
 * </p>
 *
 * @author 孟小耀
 * @since 2022-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_code_generate")
@ApiModel(value="SysCodeGenerate对象", description="代码生成")
public class SysCodeGenerate extends BaseEntity<SysCodeGenerate> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    private String id;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
