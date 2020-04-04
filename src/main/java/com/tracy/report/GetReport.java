
package com.tracy.report;

import com.spire.xls.CellRange;
import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import com.tracy.command.ReportExtract;

/**
 * Created by trcay on 2020/3/29.
 * description:整合流量报表
 */


public class GetReport {


    /***
     * 删除不需要的列，保留需要的“峰值流出流速(Mbit/s)等需要的信息”
     */
    public static void deleteOLTCol() {

        //加载文档
        Workbook wb = new Workbook();
        System.out.println("加载OLT流量报表....\n");
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
        System.out.println("OLT流量报表格式整理完成....\n");
    }




    public static void CopyOLTToReport() {

        //加载OLT文件，并获取第一张工作簿
        Workbook wbOlt = new Workbook();
        wbOlt.loadFromFile("F:\\Datareport\\OLT流量报表.xlsx");
        Worksheet sheetOlt = wbOlt.getWorksheets().get(0);

        System.out.println("加载OLT流量报表....\n");

        //加载流量报表，获取第6张工作表，并将OLT中的工作表内容复制到该工作表（OLT流量为第六张，索引为5）
        Workbook wb = new Workbook();
        wb.loadFromFile("F:\\Datareport\\接入层流量预警清单（底表）.xlsx");
        Worksheet sheetAll = wb.getWorksheets().get(5);
        System.out.println("将OLT流量报表迁移到总表....\n");
        sheetAll.copyFrom(sheetOlt);

        //保存文档
        wb.saveToFile("F:\\Datareport\\整合OLT后流量报表.xlsx", FileFormat.Version2013);

        System.out.println("OLT流量报表迁移到总表完成....\n");
    }




    //将汇聚交换机流量报表拷贝到整合后最后一张sheet

    public static void CopyDSWSheetToReport() {

        //加载汇聚交换机报表，并获取第一张工作簿
        Workbook wbOlt = new Workbook();
        wbOlt.loadFromFile("F:\\Datareport\\DSW.xlsx");
        Worksheet sheetOlt = wbOlt.getWorksheets().get(0);

        //加载加载整合过OLT报表后的总表,并新增一张sheet用来存放汇聚交换机流量
        Workbook wb = new Workbook();
        wb.loadFromFile("F:\\Datareport\\整合OLT后流量报表.xlsx");
        Worksheet cpoysheet = wb.createEmptySheet();

        System.out.println("将汇聚交换机报表的内容拷贝到总表....\n");

        //将汇聚交换机报表的内容拷贝到总表的最后一张sheet
        cpoysheet.copyFrom(sheetOlt);

        System.out.println("汇聚交换机报表的内容拷贝到总表完成....\n");

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

        //表头不复制，从第2行开始复制
        CellRange sourceRange = Copyedsheet.getCellRange(2, 1, count, 8);
        CellRange destRange = FinalDSWsheet.getCellRange(2, 1, count, 8);

        System.out.println("开始最后整合....\n");

        //false为不保留源数据的格式，用目标数据格式，true则相反

        Copyedsheet.copy(sourceRange, destRange, false);


        wbFinal.saveToFile("F:\\Datareport\\流量汇总表.xlsx", FileFormat.Version2013);


        System.out.println("完成数据迁移，生成路径：F:\\Datareport\\流量汇总表.xlsx \n");


    }


    public static void main(String[] args) {


        //从网管下载DSW.xls，需登陆系统生成报表
//        GetDSWReportUtils.downloadDSWReport();

        //把DSW.xls读出来，再写进DSW.xlsx
        ReportExtract.main(null);

        //提取和整理OLT流量报表格式
        deleteOLTCol();

        //把OLT报表导入总表
        CopyOLTToReport();

        //将交换机报表导入总表
        CopyDSWSheetToReport();

        //最终整合
        IntegratingReport();

        //用POI方式读取Excel表的结果
        GetReportResult.printResultForPOI();

    }

}

