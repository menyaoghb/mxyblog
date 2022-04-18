package com.mxy.system.entity.vo;

import com.mxy.common.core.entity.SysCodeGenerate;
import com.mxy.common.core.entity.vo.BaseVO;
import lombok.Data;
/**
 * <p>
 * 代码生成-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2022-04-18
 */
@Data
public class SysCodeGenerateVO extends BaseVO<SysCodeGenerate> {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 表名
     */
    private String tableName;

}
