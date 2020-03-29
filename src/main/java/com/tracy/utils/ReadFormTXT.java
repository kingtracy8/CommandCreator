package com.tracy.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFormTXT {


    /**
     * fucntion: 从文本文件读取数据
     *
     * @param filePath 文件路径
     * @return
     */
    public static List<String> ReadIn(String filePath) {


        List<String> dataList = new ArrayList<String>();

        try {
            //从文件路径读取txt，构建缓冲流
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "GBK");

            BufferedReader bufferedReader = new BufferedReader(isr);

            String LineData;
            //每次读取一行数据，添加到List集合并返回
            while (null != (LineData = bufferedReader.readLine())) {

                dataList.add(LineData);

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return dataList;

    }

    public static void main(String[] args) {


        List<String> ipList = ReadIn("F:\\a\\ip.txt");
        List<String> nodeList = ReadIn("F:\\a\\node.txt");
        List<String> nameList = ReadIn("F:\\a\\name.txt");

        for (int i = 0; i < ipList.size(); i++) {
            System.out.println(ipList.get(i));
        }

        for (int i = 0; i < nodeList.size(); i++) {
            System.out.println(nodeList.get(i));
        }

        for (int i = 0; i < nameList.size(); i++) {
            System.out.println(nameList.get(i));
        }

    }


}
