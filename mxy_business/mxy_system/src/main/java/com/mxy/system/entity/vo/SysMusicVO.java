package com.mxy.system.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.mxy.common.core.entity.vo.BaseVO;
import com.mxy.common.core.entity.SysMusic;

import java.util.Date;
/**
 * <p>
 * 音乐-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2022-09-13
 */
@Data
public class SysMusicVO extends BaseVO<SysMusic> {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 歌曲
     */
    private String title;
    /**
     * 作者
     */
    private String artist;
    /**
     * 链接
     */
    private String url;
    /**
     * 封面
     */
    private String pic;
    /**
     * 歌词
     */
    private String lrc;
    /**
     * 状态（0-有效 1-无效）
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
