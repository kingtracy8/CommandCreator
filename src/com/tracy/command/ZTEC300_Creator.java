package com.tracy.command;

import com.tracy.utils.FileUtils;

import java.io.IOException;
import java.util.Calendar;

public class ZTEC300_Creator {


    /**
     * fuction: 生成
     * @param slot
     * @param port
     * @param cover_vlan
     * @param inner_vlan
     * @param count
     * @throws IOException
     */
    public static void CreateC300Gpon(Integer slot, Integer port, Integer cover_vlan, Integer inner_vlan, Integer count) throws IOException {

        String FileName = FileUtils.CreateFileName("C300", slot, port, "GPON");

//      创建文件
        FileUtils.CreatePWwriter(FileName);

        FileUtils.writeLine("第一部分： 注册ONU\r\r\n");

        FileUtils.writeLine("interface gpon-olt_1/" + slot + "/" + port + "");
        FileUtils.writeLine("no shutdown");
        FileUtils.writeLine("linktrap disable");

        for (int i = 1; i <= count; i++) {

            FileUtils.writeLine("onu " + i + " type FTTH_GPON_HGU loid ");

        }

        FileUtils.writeLine("!\r\r\n" +
                "end");


        FileUtils.writeLine("\r\r\n");
        FileUtils.writeLine("第二部分：配置速率模版与业务通道\r\r\n");

        for (int i = 1; i <= count; i++) {

            String part2 = "interface gpon-onu_1/" + slot + "/" + port + ":" + i + "\r\n" +
                    "  description \r\n" +
                    "  sn-bind disable\r\n" +
                    "  tcont 1 name tcont-1 profile HG_300M\r\n" +
                    "  gemport 1 name gemport-1 tcont 1\r\n" +
                    "  gemport 1 traffic-limit downstream HG_1000M \r\n" +
                    "  gemport 2 name gemport-2 tcont 1\r\n" +
                    "  gemport 2 traffic-limit downstream HG_1000M \r\n" +
                    "  gemport 3 name gemport-3 tcont 1\r\n" +
                    "  gemport 3 traffic-limit downstream HG_1000M \r\n" +
                    "  service-port 1 vport 1 user-vlan 46 vlan 46 svlan " + cover_vlan + " \r\n" +
                    "  service-port 2 vport 2 user-vlan 41 vlan " + (inner_vlan++) + " svlan " + cover_vlan + " \r\n" +
                    "  service-port 3 vport 3 user-vlan 45 vlan 45 svlan " + cover_vlan + " \r\n" +
                    "  igmp fast-leave enable vport 3\r\n" +
                    "!\r\n" +
                    "end\r\n";
            FileUtils.writeLine(part2);

        }

        FileUtils.writeLine("\r\r\n");
        FileUtils.writeLine("第三部分：配置onu管理模版\r\r\n");

        for (int i = 1; i <= count; i++) {

            String part3 = "pon-onu-mng gpon-onu_1/"+slot+"/"+port+":"+i+"\r\n" +
                    "  service gemport-1 gemport 1 vlan 46\r\n" +
                    "  service gemport-2 gemport 2 vlan 41\r\n" +
                    "  service gemport-3 gemport 3 vlan 45\r\n" +
                    "  switchport-bind switch_0/1 veip 1\r\n" +
                    "!";

            FileUtils.writeLine(part3);
        }

        FileUtils.writeLine("\r\r\n");
        FileUtils.writeLine("第四部分： 配置组播\r\r\n");

        for (int i = 1; i <= count; i++) {

            String part4 = "igmp mvlan 50 receive-port gpon-onu_1/"+slot+"/"+port+":"+i+" vport 3";

            FileUtils.writeLine(part4);

        }

        System.out.println("生成命令成功，文件位置：" + FileName);

    }


    public static void main(String[] args) throws IOException {

        CreateC300Gpon(2, 1, 2524, 255, 5);

    }

}
