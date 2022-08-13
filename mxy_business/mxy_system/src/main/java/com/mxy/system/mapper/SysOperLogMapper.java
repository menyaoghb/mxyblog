package com.mxy.system.mapper;

import com.mxy.common.core.entity.SysOperLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxy.system.entity.vo.SysOperLogVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

    List<Map<String,Object>> statisticsLogInfo();

}
