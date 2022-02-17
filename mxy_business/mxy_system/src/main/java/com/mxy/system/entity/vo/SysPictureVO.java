package com.mxy.system.entity.vo;

import com.mxy.common.core.entity.SysPicture;
import com.mxy.common.core.entity.vo.BaseVO;
import lombok.Data;

import java.util.Date;
/**
 * <p>
 * 图片-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-18
 */
@Data
public class SysPictureVO extends BaseVO<SysPicture> {

    /**
     * 主键
     */
    private String id;
    /**
     * 图片Id
     */
    private String pictureId;
    /**
     * 图片名称
     */
    private String pictureName;
    /**
     * 图片类别
     */
    private String type;
    /**
     * 文件类型（后缀）
     */
    private String fileType;
    /**
     * 状态（0禁用 1启用）
     */
    private String status;
    /**
     * 是否删除（0未删除 1已删除）
     */
    private String isDelete;
    /**
     * 创建者
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;

}
