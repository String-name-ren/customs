package com.leader.ren.common.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


/**
 * 　　* @description: 到处Excel 工具类
 * 　　* @author 闫海豹
 * 　　* @date 2019/6/3 10:11
 *
 */
@Slf4j
public final class ExcelUtil {

    /**
     * 导出没有样式的EXCEL
     *
     * @param sheetName
     * @return
     */
    @SuppressWarnings("resource")
    public static InputStream exportDefaultExcel(String sheetName, List<String> dataHead, List<List<String>> dataBody) {
        Assert.notNull(sheetName, "sheet名称不存在！");
        Assert.notEmpty(dataHead, "导入数据头不能为空！");
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        setDefaultHSSFRow(sheet, changeDefaultData(dataHead));
        dataBody.stream().forEach(obj->{
            setDefaultHSSFRow(sheet, changeDefaultData(obj));
        });
        InputStream is = uploadExportExcel(wb);
        return is;
    }

    /**
     * 导出自定义带样式的EXCEL
     *
     * @param sheetName
     * @return
     */
    @SuppressWarnings("resource")
    public static InputStream exportStyleExcel(String sheetName, List<DataCell> dataHead, List<List<DataCell>> dataBody) {
        Assert.notNull(sheetName, "sheet名称不存在！");
        Assert.notEmpty(dataHead, "导入数据头不能为空！");
        Assert.notEmpty(dataBody, "导入数据不能为空！");
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        setDefaultHSSFRow(sheet, dataHead);
        dataBody.stream().forEach(obj->{
            setDefaultHSSFRow(sheet, obj);
        });
        InputStream is = uploadExportExcel(wb);
        return is;
    }

    /**
     * HSSFCell 设置值，样式
     * @param sheet
     * @param data
     */
    private static void setDefaultHSSFRow(HSSFSheet sheet, List<DataCell> data) {
        Assert.notNull(sheet, "sheet不存在！");
        Assert.notEmpty(data, "表头数据不存在！");

        HSSFRow row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        IntStream.range(0, data.size()).forEach(i -> {
            HSSFCell cell = row.createCell(i);
            String value = data.get(i).getData();
            cell.setCellType(data.get(i).getCellType());
            cell.setCellValue(value);
            sheet.setColumnWidth(i, data.get(i).getColumnWidth());
        });
    }

    /**
     * 导出数据转换成InputStream流返回
     * @param wb
     * @return
     */
    private static InputStream uploadExportExcel(HSSFWorkbook wb) {
        Assert.notNull(wb, "sheet不存在！");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            wb.write(os);
            is = new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            log.error("导出信息，写入文件流失败，异常信息：{}", e.getMessage());
        } finally {
            try {
                os.flush();
                os.close();
                wb.close();
            } catch (IOException e) {
                log.error("关闭件流失败，异常信息：{}", e.getMessage());
            }
        }
        return is;
    }

    /**
     * 对象值转换
     *
     * @param list
     * @return
     */
    public static List<DataCell> changeDefaultData(List<String> list) {
        Assert.notEmpty(list, "要导入数据不存在！");
        List<DataCell> datas = new ArrayList<>();
        list.stream().forEach(o -> {datas.add(
                DataCell.builder().data(o==null?"":o)
                        .cellType(Cell.CELL_TYPE_STRING)
                        .columnWidth(3000)
                        .build()
                );

        });
        return datas;

    }

    /**
    　　* @description: 内部类 存储 cell 值，字段样式 列宽信息
    　　* @author 闫海豹
    　　* @date 2019/6/3 17:56
    　　*/
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class DataCell {

        /**
         * 导入数据
         */
        private String data;
        /**
         * 导入数据类型
         */
        private int cellType;

        /**
         * 导入数据列宽
         */
        private int columnWidth;

    }

}
