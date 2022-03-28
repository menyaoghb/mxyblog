package com.mxy.system.entity.vo;

import com.mxy.common.core.entity.SysBookmarks;
import com.mxy.common.core.entity.vo.BaseVO;
import lombok.Data;

import java.util.Date;
/**
 * <p>
 * 收藏书签-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2022-03-07
 */
@Data
public class SysBookmarksVO extends BaseVO<SysBookmarks> {

    /**
     * 主键
     */
    private String id;
    /**
     * 书签链接
     */
    private String url;
    /**
     * 书签名称
     */
    private String name;
    /**
     * 书签分类
     */
    private String type;
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    /**
     * 是否删除（0未删除1已删除）
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
