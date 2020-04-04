package com.tracy.utils;

import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

public class testConverXLS {


    /**
     * 将OLT报表的格式另存为xlsx格式
     */
    public static void ConverOLTXlsToXLSX(){



        //加载文档
        Workbook wb = new Workbook();
        System.out.println("加载OLT流量报表....");
        wb.loadFromFile("F:\\Datareport\\OLT.xls");

        //获取第一张工作表
        Worksheet sheet0 = wb.getWorksheets().get(0);

        //获取第三张工作表，命名，并将第一张工作表内容复制到该工作表
        Worksheet sheet2 = wb.createEmptySheet();
        sheet2.setName("Copiedsheet");
        sheet2.copyFrom(sheet0);

        //保存文档
        wb.saveToFile("F:\\Datareport\\OLT.xlsx",FileFormat.Version2013);
        System.out.println("OLT流量报表格式转换成xlsx....");


    }


    /**
     * 将DSW报表的格式另存为xlsx格式
     */
    public static void ConverDSWXlsToXLSX(){



        //加载文档
        Workbook wb = new Workbook();
        System.out.println("加载DSW流量报表....");
        wb.loadFromFile("E:\\test\\DSW - 副本.xls");

        //获取第一张工作表
        Worksheet sheet0 = wb.getWorksheets().get(0);

        Integer OrgCount = sheet0.getLastRow();
        System.out.println("OrgCount: "+OrgCount);

        //获取第三张工作表，命名，并将第一张工作表内容复制到该工作表
        Worksheet sheet2 = wb.createEmptySheet();
        sheet2.setName("Copiedsheet");
        sheet2.copyFrom(sheet0);

        System.out.println(sheet2.getLastRow());
        //保存文档
        wb.saveToFile("E:\\test\\DSW.xlsx",FileFormat.Version2013);
        System.out.println("DSW流量报表格式转换成xlsx....");


    }



    public static void main(String[] args) {
        ConverDSWXlsToXLSX();
    }

}
