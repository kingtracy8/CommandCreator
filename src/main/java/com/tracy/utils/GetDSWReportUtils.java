package com.tracy.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by: linsong_wei@163.com
 * description: 通过URL直接从网管下载汇聚交换机报表
 */
public class GetDSWReportUtils {

    /**
     * 利用Commons包从URL下载文件
     *
     * @param url      URL
     * @param saveDir  保存到本地的路径
     * @param fileName 保存的名字
     */
    public static void downloadByApacheCommonIO(String url, String saveDir, String fileName) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(saveDir, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getURL() {

        //:TODO 构造每天报表下载的URL

        String baseUrl = "http://134.192.247.120:2003/nms/report/flux/";
        StringBuffer url = new StringBuffer("");
        url = url.append(baseUrl);

        String RPT = "RPT02407_";
        url = url.append(RPT);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_");
        //获得日期的前一天，因为每天的报表导出的是前一天的流量
        Date reportDate = getBeforeDay();

        String dateString = format.format(reportDate);

        url = url.append(dateString);
        url = url.append(dateString);
        url = url.append(dateString);
        url = url.append(dateString);

        //去掉最后一个"_"
        String temp = url.toString();
        temp = url.substring(0, url.length() - 1);
        url = new StringBuffer(temp);

        url.append(".xls");

        String URL = url.toString();
        return URL;

    }


    /**
     * 获得今天的前一天的日期
     *
     * @return 返回前一天的指定格式
     */
    public static Date getBeforeDay() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date day = new Date();
        long ms = day.getTime() - 1 * 24 * 3600 * 1000L;
        Date prevDay = new Date(ms);
        System.out.println(format.format(prevDay));
        return prevDay;
    }


    /**
     * 构造URL并下载DSW报表
     */
    public static void downloadDSWReport() {


        String url = getURL();
        String saveDir = "F:\\Datareport";
        String fileName = "DSW.xls";

        System.out.println("开始从网管中下载DSW报表....");
        downloadByApacheCommonIO(url, saveDir, fileName);
        System.out.println("下载成功，目录:F:\\Datareport\\DSW.xls...");
    }


    public static void main(String[] args) {

        downloadDSWReport();

    }


}
