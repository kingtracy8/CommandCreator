package com.tracy.utils;

import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

/**
 * Created by trcay on 2020/3/29.
 */
public class test {




    /**
     * 读取短信、OA通报
     */
    public static void printResult() {

        Workbook workbook = new Workbook();
//        workbook.loadFromFile("E:\\work\\2020年\\2020年3月\\流量日报\\0225\\接入层流量预警清单（报表整理）0225.xlsx");
        workbook.loadFromFile("F:\\Datareport\\流量汇总表.xlsx");

        Worksheet worksheet = workbook.getWorksheets().get(1);

        String Msg = worksheet.get(17, 1).getText();
        System.out.println(Msg);


        //getEnvalutedValue()为提取公式计算后的值
        String MsgContent = worksheet.get(18, 1).getEnvalutedValue();
        System.out.println(MsgContent);
        System.out.println();

        String totalDSW = worksheet.get(10,2).getEnvalutedValue();
        System.out.println("合计"+totalDSW);
//        System.out.println(worksheet.get(21, 1).getEnvalutedValue());
//        String City =worksheet.get(22, 1).getEnvalutedValue();
//        System.out.println(City);
//        String CountySide = String.valueOf(worksheet.get(24, 1).getEnvalutedValue());
//        System.out.println(CountySide);


    }


    public static void main(String[] args) {



        printResult();


       /* //加载文档
        Workbook wb = new Workbook();
        wb.loadFromFile("C:\\Users\\trcay\\Desktop\\0327\\test.xlsx");

        //获取第一个工作表
        Worksheet sheet = wb.getWorksheets().get(0);

        //复制指定单元格范围中的数据,此处是赋值第7列到第9列
        CellRange range1 = sheet.getCellRange(5,7,800,7);
        CellRange range2 = sheet.getCellRange(5,9,800,9);
        sheet.copy(range1,range2,true);
        //把第8列弄到第7列
        CellRange range3 = sheet.getCellRange(5,8,800,8);
        CellRange range4 = sheet.getCellRange(5,7,800,7);
        sheet.copy(range3,range4,true);
        //把第9列弄到第8列

        CellRange range5 = sheet.getCellRange(5,9,800,9);
        CellRange range6 = sheet.getCellRange(5,8,800,8);
        sheet.copy(range5,range6,true);

        //获得行数
        Integer cout = sheet.getLastRow();
        System.out.println(cout);

        //保存文档
        wb.saveToFile("C:\\Users\\trcay\\Desktop\\0327\\CopyRange.xlsx", FileFormat.Version2013);*/
    }
}
