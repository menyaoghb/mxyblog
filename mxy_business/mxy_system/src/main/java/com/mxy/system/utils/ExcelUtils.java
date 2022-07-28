package com.mxy.system.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * excel工具类
 */
public class ExcelUtils {

    /**
     * @Description excel导出
     * @Author 孟耀
     * @Date 2022/6/2 21:42
     */
    public static void exportExcel(HttpServletResponse response, List<?> list, String fileName, Class<?> clazz) throws IOException {
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException();
        }
        if (StringUtils.isEmpty(fileName)) {
            fileName = new Date().toString();
        }
        String sheetName = fileName;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), clazz).autoCloseStream(Boolean.FALSE).sheet(sheetName).doWrite(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 动态表头导出
     * @author: meng.yao
     * @date: 2022/7/28 16:31
     * @param: titleList-表头名称、titleCodeList-表头编码、list-导出数据
     **/
    public static void exportExcel(HttpServletResponse response, List<String> titleList, List<String> titleCodeList, List<?> list) {
        try {
            // 转换表头
            List<List<String>> titleAllList = new ArrayList<>();
            titleList.forEach(t -> {
                titleAllList.add(Collections.singletonList(t));
            });
            // 取出表头对应数据
            List<List<Object>> result = new ArrayList<>();
            list.forEach(t -> {
                // 异步执行
//                CompletableFuture.runAsync(() -> {
                List<Object> data = new ArrayList<>();
                for (String titleCode : titleCodeList) {
                    // 转换首字符大写
                    titleCode = titleCode.substring(0, 1).toUpperCase() + titleCode.substring(1);
                    Object value = null;
                    try {
                        // 反射取数据
                        Method m = t.getClass().getMethod("get" + titleCode);
                        value = m.invoke(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    data.add(value);
                }
                result.add(data);
//                });
            });
            String fileName = "sheet";
            response.addHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(0, fileName).head(titleAllList).build();
            excelWriter.write(result, writeSheet);
            // 关闭流
            excelWriter.finish();
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
