package com.mxy.system.entity.vo;

import com.mxy.common.core.entity.SysEsData;
import com.mxy.common.core.entity.vo.BaseVO;
import lombok.Data;

import java.util.Date;
/**
 * <p>
 * ES数据存储-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2022-05-09
 */
@Data
public class SysEsDataVO extends BaseVO<SysEsData> {

    /**
     * ID
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 工资
     */
    private Integer salary;
    /**
     * 公司
     */
    private String company;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 地址
     */
    private String address;
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建者
     */
    private String createUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新者
     */
    private String updateUser;
    /**
     * 是否删除
     */
    private String isDelete;
    /**
     * 备用字段一
     */
    private String fieldOne;
    /**
     * 备用字段二
     */
    private String fieldTwo;
    /**
     * 备用字段三
     */
    private String fieldThree;
    /**
     * 备用字段四
     */
    private String fieldFour;
    /**
     * 备用字段五
     */
    private String fieldFive;
    /**
     * 备用字段六
     */
    private String fieldSix;
    /**
     * 备用字段七
     */
    private String fieldSeven;
    /**
     * 备用字段八
     */
    private String fieldEight;
    /**
     * 备用字段九
     */
    private Integer fieldNine;
    /**
     * 备用字段十
     */
    private Date fieldTen;
    /**
     * 最小工资
     */
    private String minSalary;
    /**
     * 最大工资
     */
    private String maxSalary;

}
