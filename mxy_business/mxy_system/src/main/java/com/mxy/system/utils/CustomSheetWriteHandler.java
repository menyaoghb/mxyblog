package com.mxy.system.utils;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * @author bd
 */
public class CustomSheetWriteHandler implements SheetWriteHandler {

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        // 设置保护密码
        writeSheetHolder.getSheet().protectSheet("123456");
        // 锁定单元格不可选中(防止别人直接复制内容到其他excel修改)
        writeSheetHolder.getSheet().protectSheet("1");
    }
}

