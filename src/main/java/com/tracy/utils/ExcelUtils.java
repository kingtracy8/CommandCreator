package com.tracy.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by trcay on 2020/3/29.
 */
public class ExcelUtils {

    public static void main(String[] args) {

        ExcelUtils excelTest=new ExcelUtils();

        Workbook wb = excelTest.getExcel("C:\\Users\\trcay\\Desktop\\0327\\汇聚交换机流量.xls");

        if(wb==null)
            System.out.println("文件读入出错");
        else {
            excelTest.analyzeExcel(wb);
        }

    }

    public Workbook getExcel(String filePath){
        Workbook wb=null;
        File file=new File(filePath);
        if(!file.exists()){
            System.out.println("文件不存在");
            wb=null;
        }
        else {
            String fileType=filePath.substring(filePath.lastIndexOf("."));//获得后缀名
            try {
                InputStream is = new FileInputStream(filePath);
                if(".xls".equals(fileType)){
                    wb = new HSSFWorkbook(is);
                }else if(".xlsx".equals(fileType)){
                    wb = new XSSFWorkbook(is);
                }else{
                    System.out.println("格式不正确");
                    wb=null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return wb;
    }

    public void analyzeExcel(Workbook wb){
        Sheet sheet=wb.getSheetAt(0);//读取sheet(从0计数)
        int rowNum=sheet.getLastRowNum();//读取行数(从0计数)
        for(int i=4;i<=rowNum;i++){             //i=4 从第5行开始取数
            Row row= sheet.getRow(i);//获得行
            int colNum=row.getLastCellNum();//获得当前行的列数
            for(int j=2;j<7;j++){          //从第3列"电路名称"开始取数,取到"B端端口",即col=7
                Cell cell=row.getCell(j);//获取单元格
                if(cell==null)
                    System.out.print("null     ");
                else
                    System.out.print(cell.toString()+"     ");
            }
            System.out.println();
        }
    }

}
