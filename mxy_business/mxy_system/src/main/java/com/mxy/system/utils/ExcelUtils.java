package com.mxy.system.utils;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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

}
