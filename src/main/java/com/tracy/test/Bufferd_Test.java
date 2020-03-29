package com.tracy.test;

import java.io.*;

public class Bufferd_Test {

    public static void main(String[] args) throws IOException {

        String filenameString = "F:\\a\\111.txt";
//        BufferedReader bufferedReader = new BufferedReader(new FileReader("F:\\a\\111.txt"));

        InputStreamReader isr = new InputStreamReader(new FileInputStream(filenameString), "GBK");
        BufferedReader read = new BufferedReader(isr);

        String str;

        while (null != (str = read.readLine())) {

            System.out.println(str);

        }

    }

}
