package com.tracy.command;

import com.tracy.dao.DswExcelDataVO;
import com.tracy.utils.ExcelReader;
import com.tracy.utils.ExcelWriter;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by trcay on 2020/3/29.
 */
public class ReportExtract {


    private static Logger logger = Logger.getLogger(ReportExtract.class.getName());

    public static void main(String[] args) {

        // 设定Excel文件所在路径
        String excelFileName = "E:\\test\\DSW.xls";
        // 读取Excel文件内容
        List<DswExcelDataVO> readResult = ExcelReader.readExcel(excelFileName);

        // todo 进行业务操作

        // 写入数据到工作簿对象内
        Workbook workbook = ExcelWriter.exportData(readResult);


        // 以文件的形式输出工作簿对象
        FileOutputStream fileOut = null;
        try {
            String exportFilePath = "E:\\test\\DSW.xlsx";
            File exportFile = new File(exportFilePath);
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }

            fileOut = new FileOutputStream(exportFilePath);
            workbook.write(fileOut);
            fileOut.flush();
            System.out.println("输出成功，文件位置：E:\\test\\DSW.xlsx");
        } catch (Exception e) {
            logger.warning("输出Excel时发生错误，错误原因：" + e.getMessage());
        } finally {
            try {
                if (null != fileOut) {
                    fileOut.close();
                }
                if (null != workbook) {
                    workbook.close();
                }
            } catch (IOException e) {
                logger.warning("关闭输出流时发生错误，错误原因：" + e.getMessage());
            }
        }


    }


}
