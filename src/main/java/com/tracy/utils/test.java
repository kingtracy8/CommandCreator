package com.tracy.utils;

import com.spire.xls.CellRange;
import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

/**
 * Created by trcay on 2020/3/29.
 */
public class test {

    public static void main(String[] args) {
        //加载文档
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
        wb.saveToFile("C:\\Users\\trcay\\Desktop\\0327\\CopyRange.xlsx", FileFormat.Version2013);
    }
}
