package com.tracy.utils;

import com.tracy.dao.DswExcelDataVO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by trcay on 2020/3/29.
 * 将数据写入EXCEL表里
 */
public class ExcelWriter {

    private static List<String> CELL_HEADS; //列头

    static {
        // 类装载时就载入指定好的列头信息，如有需要，可以考虑做成动态生成的列头
        CELL_HEADS = new ArrayList<>();
        CELL_HEADS.add("电路名称");
        CELL_HEADS.add("A端设备");
        CELL_HEADS.add("A端端口");
        CELL_HEADS.add("B端设备");
        CELL_HEADS.add("B端端口");
        CELL_HEADS.add("电路带宽");
        CELL_HEADS.add("平均日峰值流出利用率");
        CELL_HEADS.add("平均流出流速");
    }

    /**
     * 生成Excel并写入数据信息
     *
     * @param dataList 数据列表
     * @return 写入数据后的工作簿对象
     */
    public static Workbook exportData(List<DswExcelDataVO> dataList) {


        // 生成xlsx的Excel
         Workbook workbook = new SXSSFWorkbook();

        // 如需生成xls的Excel，请使用下面的工作簿对象，注意后续输出时文件后缀名也需更改为xls

//        Workbook workbook = new HSSFWorkbook();

        // 生成Sheet表，写入第一行的列头

        Sheet sheet = buildDataSheet(workbook);

        //构建每行的数据内容
        int rowNum = 1;
        for (Iterator<DswExcelDataVO> it = dataList.iterator(); it.hasNext(); ) {
            DswExcelDataVO data = it.next();
            if (data == null) {
                continue;
            }

            //输出行数据
            Row row = sheet.createRow(rowNum++);
            //传入一个workbook对象，因为在写数值数据之前，需要改成数值格式
            convertDataToRow(data, row, workbook);
        }
        return workbook;
    }

    /**
     * 生成sheet表，并写入第一行数据（列头）
     *
     * @param workbook 工作簿对象
     * @return 已经写入列头的Sheet
     */
    private static Sheet buildDataSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet();
        // 设置列头宽度
        for (int i = 0; i < CELL_HEADS.size(); i++) {
            sheet.setColumnWidth(i, 4000);
        }
        // 设置默认行高
        sheet.setDefaultRowHeight((short) 400);
        // 构建头单元格样式
        CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
        // 写入第一行各列的数据
        Row head = sheet.createRow(0);
        for (int i = 0; i < CELL_HEADS.size(); i++) {
            Cell cell = head.createCell(i);
            cell.setCellValue(CELL_HEADS.get(i));
            cell.setCellStyle(cellStyle);
        }
        return sheet;
    }

    /**
     * 设置第一行列头的样式
     *
     * @param workbook 工作簿对象
     * @return 单元格样式对象
     */
    private static CellStyle buildHeadCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        //对齐方式设置
        style.setAlignment(HorizontalAlignment.CENTER);
        //边框颜色和宽度设置
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边框
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边框
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边框
        //设置背景颜色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //粗体字设置
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    /**
     * 将数据转换成行
     *
     * @param data     源数据
     * @param row      行对象
     * @param workbook 传入一个workbook对象，方便修改指定的列的单元格格式
     * @return
     */
    private static void convertDataToRow(DswExcelDataVO data, Row row, Workbook workbook) {
        int cellNum = 0;
        Cell cell;
        // 电路名称
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getElectricName() ? "" : data.getElectricName());
        // A端设备
        cell = row.createCell(cellNum++);
        if (null != data.getDeviceA()) {
            cell.setCellValue(data.getDeviceA());
        } else {
            cell.setCellValue("");
        }
        // A端端口
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getInterfaceA() ? "" : data.getInterfaceA());

        // B端设备
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getDeviceB() ? "" : data.getDeviceB());


        // B端端口
        cell = row.createCell(cellNum++);
        if (null != data.getInterfaceB()) {
            cell.setCellValue(data.getInterfaceB());
        } else {
            cell.setCellValue("");
        }

        // 电路带宽
        cell = row.createCell(cellNum++);
        if (null != data.getBandwidth()) {
            // cell.setCellValue(data.getBandwidth());
            //此处是数值数据
            cell.setCellValue(Double.parseDouble(data.getBandwidth()));
        } else {
            cell.setCellValue("");
        }


        // 平均日峰值流出利用率
        cell = row.createCell(cellNum++);
        if (null != data.getDayEveOutRatio()) {

            //设置成数值格式
            XSSFCellStyle contextstyle = (XSSFCellStyle) workbook.createCellStyle();
            XSSFDataFormat df = (XSSFDataFormat) workbook.createDataFormat();
            contextstyle.setDataFormat(df.getFormat("#,##0.00"));
            cell.setCellStyle(contextstyle);
            cell.setCellValue(Double.parseDouble(data.getDayEveOutRatio()));




//            cell.setCellValue(data.getDayEveOutRatio());
        } else {
            cell.setCellValue("");
        }


        // 平均流出流速
        cell = row.createCell(cellNum++);
        if (null != data.getEveOutSpeed()) {

            XSSFCellStyle contextstyle = (XSSFCellStyle) workbook.createCellStyle();
            XSSFDataFormat df = (XSSFDataFormat) workbook.createDataFormat();
            contextstyle.setDataFormat(df.getFormat("#,##0.00"));
            cell.setCellStyle(contextstyle);
            cell.setCellValue(Double.parseDouble(data.getEveOutSpeed()));
//            cell.setCellValue(data.getEveOutSpeed());
        } else {
            cell.setCellValue("");
        }


    }


}
