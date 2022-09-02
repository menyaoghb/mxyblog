package com.mxy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.common.core.entity.SysCountry;
import com.mxy.system.entity.vo.SysCountryVO;

import java.util.List;

/**
 * <p>
 * 国家省分地市区县 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-05-10
 */
public interface SysCountryService extends IService<SysCountry> {

    /**
     * 查询国家省分地市区县列表
     */
    public String getList(SysCountryVO sysCountryVO);

    /**
    * 新增国家省分地市区县
    */
    public String add(SysCountryVO sysCountryVO);

    /**
    * 编辑国家省分地市区县
    */
    public String edit(SysCountryVO sysCountryVO);

    /**
    * 删除国家省分地市区县
    */
    public String delete(SysCountryVO sysCountryVO);

    /**
     * 查询所有国家省分地市区县数据
     */
    public List<SysCountry> selectList();

    /**
     * 加载世界树
     * @return
     */
    public String worldTree();

}
