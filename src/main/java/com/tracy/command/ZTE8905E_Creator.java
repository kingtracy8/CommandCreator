package com.tracy.command;

import java.util.Arrays;

//Create　By linsong_wei@163.com 2020-03-26

public class ZTE8905E_Creator {


    /**
     * 生成8905E侧端口配置
     *
     * @param SmargroupIndex 聚合组索引
     * @param PhyInterface   物理口
     * @param vlans          VLAN列表
     */
    public static void CustomCreator(Integer SmargroupIndex, String PhyInterface, String[] vlans) {


        //逻辑口配置

        System.out.println();

        System.out.println("interface smartgroup" + SmargroupIndex + "\n" +
                "  description TODO ==============  \n" +
                "$");


        System.out.println();

        System.out.println("switchvlan-configuration\n" +
                "  interface smartgroup" + SmargroupIndex + "\n" +
                "    switchport mode hybrid\n" +
                "    switchport hybrid vlan " + ArraysFromat(vlans) + " tag\n" +
                "    switchport hybrid native vlan 3992\n" +
                "    switchport qinq tpid external 0x8100\n" +
                "  $\n" +
                "$");

        System.out.println();

        System.out.println("lacp\n" +
                "  interface smartgroup" + SmargroupIndex + "\n" +
                "    lacp mode 802.3ad\n" +
                "  $\n" +
                "$\n");


        //物理口配置


        System.out.println("interface " + PhyInterface + "\n" +
                "  description TODO ==============  \n" +
                "  no shutdown\n" +
                "$");

        System.out.println();

        System.out.println("interface " + PhyInterface + "\n" +
                "  optical-inform monitor enable\n" +
                "  negotiation auto disable\n" +
                "  speed 1000\n" +
                "  broadcast-limit percent 10\n" +
                "  multicast-limit percent 100\n" +
                "  unknowncast-limit percent 10\n" +
                "$");

        System.out.println();

        System.out.println("switchvlan-configuration\n" +
                "  interface " + PhyInterface + "\n" +
                "    switchport mode hybrid\n" +
                "    switchport hybrid vlan " + ArraysFromat(vlans) + " tag\n" +
                "    switchport hybrid native vlan 3992\n" +
                "    switchport qinq tpid external 0x8100\n" +
                "  $\n" +
                "$");

        System.out.println();

        System.out.println("lacp\n" +
                "  interface " + PhyInterface + "\n" +
                "    smartgroup " + SmargroupIndex + " mode active\n" +
                "  $");


    }


    /**
     * 将VLAN生成需要的格式
     *
     * @param data
     * @return
     */

    public static String ArraysFromat(String[] data) {

        String result = null;

        result = Arrays.toString(data);

        //去掉首尾的"[ ]"符号

        result = result.substring(1, result.length() - 1);

        result = result.trim();

        //为了符合8905E的命令格式，去除字符串直接的空格

        result = result.replaceAll(" ", "");

        return result;
    }


    public static void main(String[] args) {


        CustomCreator(8, "gei-0/1/0/41", new String[]{"3992", "3000"});

    }


}
