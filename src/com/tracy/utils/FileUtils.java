package com.tracy.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Created by trcay on 2019/8/18.
 */
public class FileUtils {

    public static PrintWriter pw;

    /**
     * 生成PrintWriter流
     *
     * @param FileName 文件名xxx.txt
     * @throws IOException
     */
    public static void CreatePWwriter(String FileName) throws IOException {

        pw = new PrintWriter(FileName);

    }

    /**
     * 向文件内写入字符
     *
     * @param data 每一行字符的数据
     */
    public static void writeLine(String data) {
        pw.println(data);
        pw.flush();
    }


    public static void write(String data) {
        pw.print(data);
        pw.flush();
    }


    /**
     * 关闭pw
     */
    public static void closeWriter() {
        pw.close();
    }



    public static String CreateFileName(String device, Integer slot, Integer port, String category) throws IOException {

        String FileName = "F:\\Creator\\"+device+category+"_" + slot + "_" + port + "_";
        Calendar cal = Calendar.getInstance();
        FileName += cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        FileName += ".txt";
        return FileName;
    }

}
