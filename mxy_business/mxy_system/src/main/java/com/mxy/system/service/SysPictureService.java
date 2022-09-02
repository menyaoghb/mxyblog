package com.mxy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.common.core.entity.SysPicture;
import com.mxy.system.entity.vo.SysPictureVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 图片 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-18
 */
public interface SysPictureService extends IService<SysPicture> {

    /**
     * 查询图片列表
     */
    public String getList(SysPictureVO sysPictureVO);

    /**
     * 新增图片
     */
    public String add(SysPictureVO sysPictureVO);

    /**
     * 编辑图片
     */
    public String edit(SysPictureVO sysPictureVO);

    /**
     * 删除图片
     */
    public String delete(SysPictureVO sysPictureVO);

    /**
     * 图片标签列表
     */
    public String getFileTypeList(SysPictureVO sysPictureVO);

    /**
     * 图片上传
     */
    public String uploadPicture(Map<String, Object> map, MultipartFile file);

}
