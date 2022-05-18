package com.mxy.test.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {
    public static void writExcel(HttpServletResponse response, List list) throws IOException {
        //将输出流封装到excelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
//         定义工作表对象
        WriteSheet sheet = EasyExcel.writerSheet(0, "sheet").head(Student.class).build();
//         往excel写入
        excelWriter.write(list, sheet);
//         关闭输出流
        excelWriter.finish();
    }
}
