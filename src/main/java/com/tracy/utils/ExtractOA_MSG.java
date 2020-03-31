package com.tracy.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

import static com.tracy.utils.ExcelReader.getWorkbook;

public class ExtractOA_MSG {

    private static Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";


    /**
     * 使用POI方式 读取短信、OA通报
     */
    public static void printResultForPOI() {


        System.out.println("POI方式读取短信和 OA通报");


        String fileName = "F:\\Datareport\\流量汇总表.xlsx";

        org.apache.poi.ss.usermodel.Workbook workbook = null;
        FileInputStream inputStream = null;

        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                logger.warning("指定的Excel文件不存在！");
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            //获得第二张工作表

            Sheet sheet = workbook.getSheetAt(1);

            //获得短信头的cell
            int MsgIndex = 16;
            Row MsgRow = sheet.getRow(MsgIndex);
            Cell Msg = MsgRow.getCell(0);
            System.out.println(Msg.getStringCellValue());
            System.out.println();

            //获得短信内容的cell
            int MsgContentIndex = 17;
            Row MsgContentRow = sheet.getRow(MsgContentIndex);
            Cell MsgCoontet = MsgContentRow.getCell(0);
            System.out.println(MsgCoontet.getStringCellValue());
            System.out.println();


            //获得OA头的cell
            int OAIndex = 19;
            Row OARow = sheet.getRow(OAIndex);
            Cell OA = OARow.getCell(0);
            System.out.println(OA.getStringCellValue());


            //获得OA （2）的cell
            int OA2Index = 20;
            Row OA2Row = sheet.getRow(OA2Index);
            Cell OA2 = OA2Row.getCell(0);
            System.out.println(OA2.getStringCellValue());

            //获得OA 城市内容的cell
            int OACityIndex = 21;
            Row OACityRow = sheet.getRow(OACityIndex);
            Cell OACityContent = OACityRow.getCell(0);
            System.out.println(OACityContent.getStringCellValue());


            //获得OA 农村内容的cell
            int OACountryIndex = 23;
            Row OACountryRow = sheet.getRow(OACountryIndex);
            Cell OACountryContent = OACountryRow.getCell(0);
            System.out.println(OACountryContent.getStringCellValue());



            /*
            if (null == MsgContentRow) {
                logger.warning("解析Excel失败，在第一行没有读取到任何数据！");
            }else {

                Cell cell;
                cell = MsgContentRow.getCell(0);
                System.out.println(cell.getStringCellValue());
            }
            */


        } catch (Exception e) {
            logger.warning("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());

        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.warning("关闭数据流出错！错误信息：" + e.getMessage());

            }
        }
    }


    public static void main(String[] args) {



        printResultForPOI();
    }


}
