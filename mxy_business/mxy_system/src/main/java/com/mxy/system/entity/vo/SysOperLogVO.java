package com.mxy.system.entity.vo;

import java.util.Date;
import lombok.Data;
import com.mxy.common.core.entity.vo.BaseVO;
import com.mxy.common.core.entity.SysOperLog;

/**
 * <p>
 * 操作日志记录-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Data

public class SysOperLogVO extends BaseVO<SysOperLog> {

    /**
     * 日志主键
     */
    private String operId;
    /**
     * 模块标题
     */
    private String title;
    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer businessType;
    /**
     * 方法名称
     */
    private String method;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    private Integer operatorType;
    /**
     * 操作人员
     */
    private String operName;
    /**
     * 请求URL
     */
    private String operUrl;
    /**
     * 主机地址
     */
    private String operIp;
    /**
     * 操作地点
     */
    private String operLocation;
    /**
     * 请求参数
     */
    private String operParam;
    /**
     * 返回参数
     */
    private String jsonResult;
    /**
     * 请求时长
     */
    private Integer resTime;
    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;
    /**
     * 错误消息
     */
    private String errorMsg;
    /**
     * 操作时间
     */
    private Date operTime;

}
