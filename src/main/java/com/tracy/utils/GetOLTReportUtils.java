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
public class GetOLTReportUtils {

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

        String dateString = format.format(new Date());

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
     * 构造URL并下载DSW报表
     */
    public static void downloadDSWReport(){


        String url = "http://134.192.247.88:8090/iareport/exportExcel.do?reportId=RPT000037&reportName=OLT%E4%B8%8A%E8%81%94%E7%AB%AF%E5%8F%A3%E6%B5%81%E9%87%8F&viewType=D&nodeCode=NOD999&timePara=2020-03-31_2020-03-31&recType=G&hasNodeItem=1&filterNode=%E6%B2%B3%E6%B1%A0%E5%B8%82&filterNodeLevel=2&isCollectReport=N&expandNodeLevel=4&minNodeLevel=2&maxNodeLevel=4&selectSheet=OLT%E4%B8%8A%E8%81%94%E5%8F%A3%E6%B5%81%E9%87%8F&colName=%E5%9C%B0%E5%B8%82%2C%E5%8C%BA%E5%8E%BF%2C%E7%BB%B4%E6%8A%A4%E7%8F%AD%E7%BB%84%2C%E8%AE%BE%E5%A4%87%E5%90%8D%E7%A7%B0%2C%E8%AE%BE%E5%A4%87IP%E5%9C%B0%E5%9D%80%2C%E8%AE%BE%E5%A4%87%E5%9E%8B%E5%8F%B7%2C%E7%AB%AF%E5%8F%A3%2C%E7%AB%AF%E5%8F%A3%E5%B8%A6%E5%AE%BD(Mbit%2Fs)%2C%E5%B9%B3%E5%9D%87%E6%B5%81%E5%85%A5%E6%B5%81%E9%80%9F(Mbit%2Fs)%2C%E5%B9%B3%E5%9D%87%E6%B5%81%E5%87%BA%E6%B5%81%E9%80%9F(Mbit%2Fs)%2C%E5%B3%B0%E5%80%BC%E6%B5%81%E5%85%A5%E6%B5%81%E9%80%9F(Mbit%2Fs)%2C%E5%B3%B0%E5%80%BC%E6%B5%81%E5%87%BA%E6%B5%81%E9%80%9F(Mbit%2Fs)%2C%E5%B3%B0%E5%80%BC%E5%B9%B3%E5%9D%87%E6%B5%81%E5%85%A5%E6%B5%81%E9%80%9F(Mbit%2Fs)%2C%E5%B3%B0%E5%80%BC%E5%B9%B3%E5%9D%87%E6%B5%81%E5%87%BA%E6%B5%81%E9%80%9F(Mbit%2Fs)%2C%E5%B9%B3%E5%9D%87%E6%B5%81%E5%85%A5%E5%88%A9%E7%94%A8%E7%8E%87(%25)%2C%E5%B9%B3%E5%9D%87%E6%B5%81%E5%87%BA%E5%88%A9%E7%94%A8%E7%8E%87(%25)%2C%E5%B3%B0%E5%80%BC%E6%B5%81%E5%85%A5%E5%88%A9%E7%94%A8%E7%8E%87(%25)%2C%E5%B3%B0%E5%80%BC%E6%B5%81%E5%87%BA%E5%88%A9%E7%94%A8%E7%8E%87(%25)%2C%E5%B3%B0%E5%80%BC%E5%B9%B3%E5%9D%87%E6%B5%81%E5%85%A5%E5%88%A9%E7%94%A8%E7%8E%87(%25)%2C%E5%B3%B0%E5%80%BC%E5%B9%B3%E5%9D%87%E6%B5%81%E5%87%BA%E5%88%A9%E7%94%A8%E7%8E%87(%25)%2C%E7%9E%AC%E6%97%B6%E6%B5%81%E5%85%A5%E5%88%A9%E7%94%A8%E7%8E%87%E8%B6%85%E8%BF%8760%25%E6%97%B6%E9%97%B4%E6%AF%94%E4%BE%8B(%25)%2C%E7%9E%AC%E6%97%B6%E6%B5%81%E5%87%BA%E5%88%A9%E7%94%A8%E7%8E%87%E8%B6%85%E8%BF%8760%25%E6%97%B6%E9%97%B4%E6%AF%94%E4%BE%8B(%25)%2C%E5%B8%A6%E5%AE%BD%E7%94%A8%E6%88%B7%E6%95%B0(%E4%B8%AA)%2Ciptv%E7%94%A8%E6%88%B7%E6%95%B0(%E4%B8%AA)&sort=&order=&rand=13214424";
        String saveDir = "F:\\Datareport";
        String fileName = "OLT.xls";

        System.out.println("开始从网管中下载OLT报表....");
        downloadByApacheCommonIO(url, saveDir, fileName);
        System.out.println("下载成功，目录:F:\\Datareport\\OLT.xls...");
    }


    public static void main(String[] args) {

        downloadDSWReport();

    }


}
