package com.mxy.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.mxy.common.core.entity.vo.BaseEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 短信发送日志
 * </p>
 *
 * @author 孟小耀
 * @since 2022-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_sms_send_log")
@ApiModel(value="SysSmsSendLog对象", description="短信发送日志")
public class SysSmsSendLog extends BaseEntity<SysSmsSendLog> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 接收号码
     */
    private String phone;

    /**
     * 入参报文
     */
    private String request;

    /**
     * 出参报文
     */
    private String response;

    /**
     * 发送时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
