package com.mxy.system.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.mxy.common.core.entity.vo.BaseVO;
import com.mxy.common.core.entity.SysSmsSendLog;

import java.util.Date;
/**
 * <p>
 * 短信发送日志-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2022-09-02
 */
@Data
public class SysSmsSendLogVO extends BaseVO<SysSmsSendLog> {

    /**
     * 主键
     */
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
    private Date createTime;

}
