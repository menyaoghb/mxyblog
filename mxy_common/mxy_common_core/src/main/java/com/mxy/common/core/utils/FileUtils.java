package com.mxy.common.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Description 文件相关 工具类
 * @Date 2021/8/3 17:58
 */
public class FileUtils {
//
//
//    public Map<String, Object> download(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Map<String, Object> map = new HashMap<>();
//        InputStream fileInputStream = FileUtils.excelToInputStream(FileUtils.createExcel("模板.xlsx", new ArrayList<>()));
//        //设置Http响应头告诉浏览器下载这个附件
//        response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode("模板.xlsx", "UTF-8"));
//        OutputStream outputStream = response.getOutputStream();
//        byte[] bytes = new byte[2048];
//        int len = 0;
//        while ((len = fileInputStream.read(bytes)) > 0) {
//            outputStream.write(bytes, 0, len);
//        }
//        fileInputStream.close();
//        outputStream.close();
//        map.put("msg", "succ");
//        return map;
//    }
//
//
//    /**
//     * @Description: 生成excel文件
//     * @Param: [title-表格名称, list-写入excel中的数据,head-表头]
//     * @Date: 2021/8/3
//     */
//    public static <T> HSSFWorkbook createExcel(String title, List<Map<String, String>> list) {
//        //声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        try {
//            //生成一个表格
//            HSSFSheet sheet = workbook.createSheet(title);
//            //设置表格默认列宽度为30个字节
//            sheet.setDefaultColumnWidth(30);
//            //标题
//            HSSFCellStyle titleStyle = workbook.createCellStyle();
//            //格式化单元格 格式 为 文本
//            HSSFDataFormat format = workbook.createDataFormat();
//            titleStyle.setDataFormat(format.getFormat("@"));
//            //标题字体
//            HSSFFont titleFont = workbook.createFont();
//            //字号
//            titleFont.setFontHeightInPoints((short) 13);
//            titleStyle.setFont(titleFont);
//            //水平居中
//            titleStyle.setAlignment(HorizontalAlignment.CENTER);
//            //垂直居中
//            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//            //设置默认行高
//            sheet.setDefaultRowHeight((short) 540);
//            //设置表头
//            HSSFRow row = sheet.createRow(0);
//            HSSFCell titleCell = row.createCell(0);
//            titleCell.setCellValue(new HSSFRichTextString("工单类型"));
//            titleCell.setCellStyle(titleStyle);
//            titleCell = row.createCell(1);
//            titleCell.setCellValue(new HSSFRichTextString("申诉ID"));
//            titleCell.setCellStyle(titleStyle);
//            /*列*/
//            int index = 0;
//            for (int i = 0; i < list.size(); i++) {
//                Map<String, String> map = list.get(i);
//                String id = MapUtils.getString(map, "id");
//                String idType = MapUtils.getString(map, "type");
//                index++;
//                row = sheet.createRow(index);
//                HSSFCell cellType = row.createCell(0);
//                cellType.setCellValue(idType);
//                cellType.setCellStyle(titleStyle);
//                HSSFCell cellId = row.createCell(1);
//                cellId.setCellValue(id);
//                cellId.setCellStyle(titleStyle);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                workbook.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return workbook;
//    }
//
//    /**
//     * Excel转InputStream
//     */
//    public static InputStream excelToInputStream(HSSFWorkbook wb) {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        InputStream is = null;
//        try {
//            wb.write(os);
//            byte[] b = os.toByteArray();
//            is = new ByteArrayInputStream(b);
//            os.close();
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (null != is) {
//                try {
//                    is.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                os.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return is;
//    }
//

    /**
     * InputStream转File
     */
    public static void inputStreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
