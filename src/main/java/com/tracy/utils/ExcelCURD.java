
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


    /**
     * 将OLT报表的格式另存为xlsx格式
     */
    public static void ConverOLTXlsToXLSX() {

        Workbook wb = new Workbook();
        System.out.println("加载OLT流量报表....");
        wb.loadFromFile("F:\\Datareport\\OLT.xls");

        Worksheet sheet = wb.getWorksheets().get(0);
        sheet.saveToFile("F:\\Datareport\\OLT.xlsx", String.valueOf(FileFormat.Version2013));

        System.out.println("OLT流量报表格式转换成xlsx....");

//        wb.saveToFile("F:\\Datareport\\OLT.xlsx");
//        wb.dispose();
//        System.out.println("OLT流量报表格式转换成xlsx....");

    }


    public static void ConverDSWXlsToXLSX() {

        Workbook wb = new Workbook();
        System.out.println("加载汇聚交换机流量报表....");
        wb.loadFromFile("F:\\Datareport\\DSW.xls");


        wb.saveToFile("F:\\Datareport\\DSW.xlsx");
        wb.dispose();
        System.out.println("汇聚交换机流量报表格式转换成xlsx....");

    }


    /***
     * 删除不需要的列，保留需要的“峰值流出流速(Mbit/s)等需要的信息”
     */
    public static void deleteOLTCol() {

        //加载文档
        Workbook wb = new Workbook();
        System.out.println("加载OLT流量报表....");
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
        System.out.println("OLT流量报表格式整理完成....");
    }

    public static void deleteDswCol() {

        Workbook wb = new Workbook();

        //假如后缀是.xls格式，结果只能返回200行，原因不明  linsong_wei@163.com  ， xlsx文件可处理大于200行数据

        System.out.println("加载汇聚交换机流量报表....");

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

        System.out.println("汇聚交换机流量报表格式整理完成....");
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


    public static void CopyOLTToReport() {

        //加载OLT文件，并获取第一张工作簿
        Workbook wbOlt = new Workbook();
        wbOlt.loadFromFile("F:\\Datareport\\OLT流量报表.xlsx");
        Worksheet sheetOlt = wbOlt.getWorksheets().get(0);

        System.out.println("加载OLT流量报表....");

        //加载流量报表，获取第6张工作表，并将OLT中的工作表内容复制到该工作表（OLT流量为第六张，索引为5）
        Workbook wb = new Workbook();
        wb.loadFromFile("F:\\Datareport\\接入层流量预警清单（底表）.xlsx");
        Worksheet sheetAll = wb.getWorksheets().get(5);
        System.out.println("将OLT流量报表迁移到总表....");
        sheetAll.copyFrom(sheetOlt);

        //保存文档
        wb.saveToFile("F:\\Datareport\\整合OLT后流量报表.xlsx", FileFormat.Version2013);

        System.out.println("OLT流量报表迁移到总表完成....");
    }


    //TODO：可能是表头不一样的问题，待处理

    public static void CopyDSWToReport() {


        //1、取得要复制的数据，即交换机流量报表第

        //加载文档
        Workbook wbDSW = new Workbook();
        wbDSW.loadFromFile("F:\\Datareport\\汇聚交换机流量报表.xlsx");

        Workbook wbOLT = new Workbook();
        wbOLT.loadFromFile("F:\\Datareport\\整合OLT后流量报表.xlsx");


        //获取第一个工作表
        Worksheet DSWsheet = wbDSW.getWorksheets().get(0);

        Worksheet OLTsheet = wbOLT.getWorksheets().get(8);


        //获得行数,有可能包括表头需要减掉
        Integer count = DSWsheet.getLastRow();


        //复制指定单元格范围中的数据,此处是赋值第7列到第9列
        CellRange sourceRange = DSWsheet.getCellRange(2, 1, count, 8);
        CellRange destRange = OLTsheet.getCellRange(2, 1, count, 8);
        OLTsheet.copy(sourceRange, destRange, true);

        wbOLT.saveToFile("F:\\Datareport\\整合后流量报表.xlsx", FileFormat.Version2013);


        System.out.println("完成数据迁移");

    }


    //将汇聚交换机流量报表拷贝到整合后最后一张sheet

    public static void CopyDSWSheetToReport() {

        //加载汇聚交换机报表，并获取第一张工作簿
        Workbook wbOlt = new Workbook();
        wbOlt.loadFromFile("F:\\Datareport\\汇聚交换机流量报表.xlsx");
        Worksheet sheetOlt = wbOlt.getWorksheets().get(0);

        //加载加载整合过OLT报表后的总表,并新增一张sheet用来存放汇聚交换机流量
        Workbook wb = new Workbook();
        wb.loadFromFile("F:\\Datareport\\整合OLT后流量报表.xlsx");
        Worksheet cpoysheet = wb.createEmptySheet();

        System.out.println("将汇聚交换机报表的内容拷贝到总表....");

        //将汇聚交换机报表的内容拷贝到总表的最后一张sheet
        cpoysheet.copyFrom(sheetOlt);

        System.out.println("汇聚交换机报表的内容拷贝到总表完成....");

        //保存文档
        wb.saveToFile("F:\\Datareport\\拷贝后流量报表.xlsx", FileFormat.Version2013);


    }


    /**
     * 最终整合方法，将交换机的流量拷贝到正确的位置，保持原有公式
     */
    public static void IntegratingReport() {


        //将总表的最后一张sheet，也就是汇聚交换机流量数据 复制到 原 汇聚交换机流量 有公式的那一页

        Workbook wbFinal = new Workbook();
        wbFinal.loadFromFile("F:\\Datareport\\拷贝后流量报表.xlsx");

        Worksheet FinalDSWsheet = wbFinal.getWorksheets().get(4);
        Worksheet Copyedsheet = wbFinal.getWorksheets().get(9);

        //获得行数
        Integer count = Copyedsheet.getLastRow();

        //表头不同，从第5行开始复制
        CellRange sourceRange = Copyedsheet.getCellRange(5, 1, count, 8);
        CellRange destRange = FinalDSWsheet.getCellRange(2, 1, count, 8);

        System.out.println("开始最后整合....");

        //false为不保留源数据的格式，用目标数据格式，true则相反

        Copyedsheet.copy(sourceRange, destRange, false);


        wbFinal.saveToFile("F:\\Datareport\\流量汇总表.xlsx", FileFormat.Version2013);


        System.out.println("完成数据迁移，生成路径：F:\\Datareport\\流量汇总表.xlsx ");


    }

    /**
     * 读取短信、OA通报
     */
    public static void printResult() {


//        System.out.println("Spire方式读取短信和 OA通报");

        Workbook workbook = new Workbook();
        workbook.loadFromFile("F:\\Datareport\\流量汇总表.xlsx");

        Worksheet worksheet = workbook.getWorksheets().get(1);

        String Msg = worksheet.get(17, 1).getText();
        System.out.println(Msg);


        //getEnvalutedValue()为提取公式计算后的值
        String MsgContent = worksheet.get(18, 1).getEnvalutedValue();
        System.out.println(MsgContent);
        System.out.println();
        System.out.println(String.valueOf(worksheet.get(21, 1).getEnvalutedValue()));
        String City = String.valueOf(worksheet.get(22, 1).getEnvalutedValue());
        System.out.println(City);
        String CountySide = String.valueOf(worksheet.get(24, 1).getEnvalutedValue());
        System.out.println(CountySide);

    }


    public static void main(String[] args) {

        //转换OLT报表格式成为XLSX,还是200行
//        ConverOLTXlsToXLSX();

        //转换DSW报表格式成为XLSX
//        ConverDSWXlsToXLSX();

        //提取和整理OLT流量报表格式
        deleteOLTCol();

        //提取整理交换机报表格式
        deleteDswCol();

        //把OLT报表导入总表
        CopyOLTToReport();

        //将交换机报表导入总表
        CopyDSWSheetToReport();

        //最终整合
        IntegratingReport();


        //spire方式读取短信和OA内容  正确读时间，但无法正确读出公式的计算值
//        printResult();

        //尝试用POI方式读取，正确读出公式的计算值，但无法正确读出时间
        ExtractOA_MSG.printResultForPOI();

    }

}

