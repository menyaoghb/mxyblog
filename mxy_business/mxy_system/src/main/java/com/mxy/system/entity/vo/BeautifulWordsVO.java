package com.mxy.system.entity.vo;

import lombok.Data;
import com.mxy.common.core.entity.vo.BaseVO;
import com.mxy.common.core.entity.BeautifulWords;

import java.util.Date;

/**
 * <p>
 * 优美的句子-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-06
 */
@Data

public class BeautifulWordsVO extends BaseVO<BeautifulWords> {

    /**
     * 主键
     */
    private String id;
    /**
     * 优美的句子
     */
    private String beautifulWords;
    /**
     * 标签
     */
    private String label;
    /**
     * 灵感（句子来源）
     */
    private String inspiration;
    /**
     * 类型（考虑在不同地方引用不同类型的句子）
     */
    private String type;
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    /**
     * 是否删除
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
