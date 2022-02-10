package com.mxy.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mxy.common.core.entity.vo.BaseEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 操作日志记录
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oper_log")
@ApiModel(value="SysOperLog对象", description="操作日志记录")
public class SysOperLog extends BaseEntity<SysOperLog> {

    private static final long serialVersionUID=1L;

    /**
     * 日志主键
     */
      @TableId(value = "oper_id", type = IdType.UUID)
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


    @Override
    protected Serializable pkVal() {
        return this.operId;
    }

}
