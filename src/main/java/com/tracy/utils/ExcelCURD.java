
package com.tracy.utils;

import com.spire.xls.CellRange;
import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;


/**
 * Created by trcay on 2020/3/29.
 * description:对excel表进行增删改查操作
 */


public class ExcelCURD {


    /***
     * 删除不需要的列，保留需要的“峰值流出流速(Mbit/s)等需要的信息”
     */
    public static void deleteOLTCol() {

        //加载文档
        Workbook wb = new Workbook();
        wb.loadFromFile("F:\\Datareport\\OLT.xlsx");

        //获取工作表
        Worksheet sheet = wb.getWorksheets().get(0);
        //从第9列开始删除，删除9列在内的4列
        sheet.deleteColumn(9, 4);
        //经过上一次删除后，索引重新从1开始，从第11列开始，删除11列在内的4列
        sheet.deleteColumn(11, 4);
        //保存文档
        wb.saveToFile("F:\\Datareport\\OLT流量报表.xlsx");
        wb.dispose();
        System.out.println("生成成功，目录：F:\\Datareport\\OLT流量报表.xlsx");
    }

    public static void deleteDswCol() {

        Workbook wb = new Workbook();

        //假如后缀是.xls格式，结果只能返回200行，原因不明  linsong_wei@163.com

        wb.loadFromFile("F:\\Datareport\\DSW.xlsx");

        //获取工作表
        Worksheet sheet = wb.getWorksheets().get(0);
        //从第9列开始删除，删除9列在内的4列
        sheet.deleteColumn(8, 1);
        sheet.deleteColumn(9, 1);
        sheet.deleteColumn(10, 19);
        sheet.deleteColumn(11, 20);
        sheet.deleteColumn(1, 2);

        //保存文档
        wb.saveToFile("F:\\Datareport\\DeleteDSWColumn.xlsx");
        wb.dispose();

        //由于原表里 平均流出流速 和 平均日峰值流出利用率 是反过来的，所以需要将这两列互换
        RotateCol();

        System.out.println("生成成功，目录：F:\\Datareport\\汇聚交换机流量报表.xlsx");
    }

    /**
     * 由于原表里 平均流出流速 和 平均日峰值流出利用率 是反过来的，所以需要将这两列互换
     */
    public static void RotateCol() {

        //加载文档
        Workbook wb = new Workbook();
        wb.loadFromFile("F:\\Datareport\\DeleteDSWColumn.xlsx");

        //获取第一个工作表
        Worksheet sheet = wb.getWorksheets().get(0);

        //获得行数
        Integer count = sheet.getLastRow();

        //复制指定单元格范围中的数据,此处是赋值第7列到第9列
        CellRange range1 = sheet.getCellRange(5, 7, count, 7);
        CellRange range2 = sheet.getCellRange(5, 9, count, 9);
        sheet.copy(range1, range2, true);
        //把第8列弄到第7列
        CellRange range3 = sheet.getCellRange(5, 8, count, 8);
        CellRange range4 = sheet.getCellRange(5, 7, count, 7);
        sheet.copy(range3, range4, true);
        //把第9列弄到第8列 即完成替换
        CellRange range5 = sheet.getCellRange(5, 9, count, 9);
        CellRange range6 = sheet.getCellRange(5, 8, count, 8);
        sheet.copy(range5, range6, true);

        //把多余的列删除
        sheet.deleteColumn(9, 1);


        //保存文档
        wb.saveToFile("F:\\Datareport\\汇聚交换机流量报表.xlsx", FileFormat.Version2013);

    }


    public static void main(String[] args) {
       //提取汇聚交换机流量报表
        deleteDswCol();
       //提取OLT流量报表
        deleteOLTCol();
    }

}

