package com.mxy.system.service;

import com.mxy.common.core.entity.BeautifulWords;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.BeautifulWordsVO;

/**
 * <p>
 * 优美的句子 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-06
 */
public interface BeautifulWordsService extends IService<BeautifulWords> {

    /**
     * 获取优美的句子列表
     */
    public String getList(BeautifulWordsVO beautifulWordsVO);

    /**
    * 新增优美的句子
    */
    public String add(BeautifulWordsVO beautifulWordsVO);

    /**
    * 编辑优美的句子
    */
    public String edit(BeautifulWordsVO beautifulWordsVO);

    /**
    * 删除优美的句子
    */
    public String delete(BeautifulWordsVO beautifulWordsVO);

    /**
     * 佳句-标签/来源列表
     */
    public String getAdviceList(BeautifulWordsVO beautifulWordsVO);

    /**
     * 登录页随机获取一条句子
     */
    public String getRandWord();

}
