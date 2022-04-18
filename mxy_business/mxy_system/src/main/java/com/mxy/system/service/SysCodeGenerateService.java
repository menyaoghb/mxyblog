package com.mxy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.common.core.entity.SysCodeGenerate;
import com.mxy.system.entity.vo.SysCodeGenerateVO;

/**
 * <p>
 * 代码生成 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-04-18
 */
public interface SysCodeGenerateService extends IService<SysCodeGenerate> {

    /**
     * 获取系统数据库表名
     */
    public String getTableName();

    /**
     * 代码生成
     */
    public String codeGenerate(SysCodeGenerateVO sysCodeGenerateVO);


}
