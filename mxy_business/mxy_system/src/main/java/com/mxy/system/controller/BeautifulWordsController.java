package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.BeautifulWordsVO;
import com.mxy.system.service.BeautifulWordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 优美的句子 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-06
 */
@Api(value = "优美的句子",tags = "优美的句子")
@RestController
@RequestMapping("/system/beautifulWords")
public class BeautifulWordsController {

    @Autowired
    public BeautifulWordsService beautifulWordsService;

    /**
     * @Description 获取优美的句子列表
     * @author 孟小耀
     * @date 2021-08-06
     */
    @SysLog(module = "优美的句子列表")
    @ApiOperation(value = "获取优美的句子列表")
    @PostMapping("/getList")
    public String getList(@RequestBody BeautifulWordsVO beautifulWordsVO) {
        return beautifulWordsService.getList(beautifulWordsVO);
    }

    /**
     * @Description 新增优美的句子
     * @author 孟小耀
     * @date 2021-08-06
     */
    @SysLog(module = "新增优美的句子", operType = OperType.ADD)
    @ApiOperation(value = "新增优美的句子")
    @PostMapping("/add")
    public String add(@RequestBody BeautifulWordsVO beautifulWordsVO) {
        return beautifulWordsService.add(beautifulWordsVO);
        }

    /**
     * @Description 编辑优美的句子
     * @author 孟小耀
     * @date 2021-08-06
     */
    @SysLog(module = "编辑优美的句子", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑优美的句子")
    @PostMapping("/edit")
    public String edit(@RequestBody BeautifulWordsVO beautifulWordsVO) {
        return beautifulWordsService.edit(beautifulWordsVO);
        }

    /**
     * @Description 删除优美的句子
     * @author 孟小耀
     * @date 2021-08-06
     */
    @SysLog(module = "删除优美的句子", operType = OperType.DELETE)
    @ApiOperation(value = "删除优美的句子")
    @PostMapping("/delete")
    public String delete(@RequestBody BeautifulWordsVO beautifulWordsVO) {
        return beautifulWordsService.delete(beautifulWordsVO);
        }

    /**
     * @Description 佳句-标签/来源列表
     * @author 孟小耀
     * @date 2021-08-06
     */
    @SysLog(module = "佳句-标签/来源列表")
    @ApiOperation(value = "佳句-标签/来源列表")
    @PostMapping("/getAdviceList")
    public String getAdviceList(@RequestBody BeautifulWordsVO beautifulWordsVO) {
        return beautifulWordsService.getAdviceList(beautifulWordsVO);
    }
}

