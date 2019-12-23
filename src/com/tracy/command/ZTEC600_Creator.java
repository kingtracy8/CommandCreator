package com.tracy.command;

import com.tracy.utils.FileUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static com.tracy.utils.ReadFormTXT.ReadIn;

/**
 * Created by trcay on 2019/9/18.
 */
public class ZTEC600_Creator {


    /**
     * function: 生成GPON脚本
     *
     * @param slot       槽位
     * @param port       端口
     * @param cover_vlan 外层VLAN号
     * @param inner_vlan 内层VLAN号
     * @param count      数量
     */
    public static void CreateC600GPON(Integer slot, Integer port, Integer cover_vlan, Integer inner_vlan, Integer count, List<String> loidList,List<String> desList) throws IOException {

//      创建文件并调用写入工具类, /r/n为回车换行转义字符，让writeLine方法识别换行

        String FileName = "F:\\Creator\\C600GPON_" + slot + "_" + port + "_";
        Calendar cal = Calendar.getInstance();
        FileName += cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        FileName += ".txt";
        FileUtils.CreatePWwriter(FileName);

        //假如 count == loid 的数量，说明没漏

        /*if(count==loidList.size()){

        }*/


        FileUtils.writeLine("第一部分：添加ONU\r\n");
        FileUtils.writeLine("interface gpon_olt-1/" + slot + "/" + port);

        for (int i = 1; i <= count; i++) {
            int index = i;
            FileUtils.writeLine("onu " + i + "  type FTTH_GPON_HGU loid"+" "+loidList.get(--index));
        }

        FileUtils.writeLine("\r\n");
        FileUtils.writeLine("第二部分：配置onu上行模板\r\n");

        for (int i = 1; i <= count; i++) {
            String part2 = "\r\n" +
                    "interface gpon_onu-1/" + slot + "/" + port + ":" + i + "\r\n" +
                    "  tcont 1 name tcont-1 profile HG_300M\r\n" +
                    "  sn-bind disable\r\n" +
                    "  gemport 1 name gemport-1 tcont 1\r\n" +
                    "$";
            FileUtils.writeLine(part2);
        }


        FileUtils.writeLine("\r\n");
        FileUtils.writeLine("第三部分：配置onu的扩展属性\r\n");
        for (int i = 1; i <= count; i++) {
            String part3 = "pon-onu-mng gpon_onu-1/" + slot + "/" + port + ":" + i + "\r\n" +
                    "  vlan port veip_1 mode trunk\r\n" +
                    "  vlan port veip_1 vlan 41,45-46\r\n" +
                    "  mvlan 50\r\n" +
                    "  service eth_service1 gemport 1 vlan 41\r\n" +
                    "  service eth_service2 gemport 1 vlan 45\r\n" +
                    "  service eth_service3 gemport 1 vlan 46\r\n" +
                    "$";
            FileUtils.writeLine(part3);
        }


        FileUtils.writeLine("\r\n");
        FileUtils.writeLine("第四部分：配置onu的业务vlan\r\n");
        for (int i = 1; i <= count; i++) {
            String part4 = "interface vport-1/" + slot + "/" + port + "." + i + ":1\r\n" +
                    "  service-port 1 user-vlan 46 vlan 46 svlan " + cover_vlan + "\r\n" +
                    "  service-port 2 user-vlan 41 vlan " + (inner_vlan++) + " svlan " + cover_vlan + "\r\n" +
                    "  service-port 3 user-vlan 45 vlan 45 svlan " + cover_vlan + "\r\n" +
                    "  qos traffic-policy HG_1000M direction egress\r\n" +
                    "  qos traffic-policy HG_300M direction ingress\r\n" +
                    "  igmp fast-leave enable\r\n" +
                    "$";
            FileUtils.writeLine(part4);
        }


        FileUtils.writeLine("\r\n");
        FileUtils.writeLine("第五部分：配置onu的IPTV组播\r\n");

        FileUtils.writeLine("igmp mvlan 50\r\n");

        for (int i = 1; i <= count; i++) {
            String part5 = "receive-port vport-1/" + slot + "/" + port + "." + i + ":1";
            FileUtils.writeLine(part5);
        }

        System.out.println("生成命令成功，文件位置：" + FileName);

    }


    /**
     * @param slot       槽位
     * @param port       端口
     * @param cover_vlan 外层VLAN号
     * @param inner_vlan 内层VLAN号
     * @param count      数量
     */
    public static void CreateC600EPON(Integer slot, Integer port, Integer cover_vlan, Integer inner_vlan, Integer count) throws IOException {

        String FileName = "F:\\Creator\\C600EPON_" + slot + "_" + port + "_";
        Calendar cal = Calendar.getInstance();
        FileName += cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        FileName += ".txt";
        FileUtils.CreatePWwriter(FileName);


        FileUtils.writeLine("第一部分：添加ONU\r\n");
        FileUtils.writeLine("interface gpon_olt-1/" + slot + "/" + port);

        for (int i = 1; i <= count; i++) {

            FileUtils.writeLine("onu " + i + " type FTTH_EPON_HGU loid ");
        }

        FileUtils.writeLine("\r\n");
        FileUtils.writeLine("第二部分:配置onu模板\r\n");

        for (int i = 1; i <= count; i++) {

            String part2 = "interface epon_onu-1/" + slot + "/" + port + ":" + i + "\r\n" +
                    " description                     \r\n" +
                    " sla-profile HG_UP300M_DW1000M vport 1\r\n" +
                    "$\r\n";
            FileUtils.writeLine(part2);
        }


        FileUtils.writeLine("\r\n");
        FileUtils.writeLine("第三部分：配置onu vlan\r\n");

        for (int i = 1; i <= count; i++) {

            String part3 = "interface vport-1/" + slot + "/" + port + "." + i + ":1\r\n" +
                    "  service-port 1 user-vlan 46 vlan 46 svlan " + cover_vlan + "\r\n" +
                    "  service-port 2 user-vlan 41 vlan " + (inner_vlan++) + " svlan " + cover_vlan + "\r\n" +
                    "  service-port 3 user-vlan 45 vlan 45 svlan " + cover_vlan + "\r\n" +
                    "  qos traffic-policy HG_1000M direction egress\r\n" +
                    "  igmp fast-leave enable\r\n" + "$";
            FileUtils.writeLine(part3);
        }


        FileUtils.writeLine("\r\n");
        FileUtils.writeLine("第四部分：配置onu的iptv组播\r\n");

        FileUtils.writeLine("igmp mvlan 50\r\n");

        for (int i = 1; i <= count; i++) {
            String part4 = "receive-port vport-1/" + slot + "/" + port + "." + i + ":1";
            FileUtils.writeLine(part4);
        }

        System.out.println("生成命令成功，文件位置：" + FileName);

    }


    /**
     * function: Version 2  生成GPON脚本,通过文件读入loid、描述等
     *
     * @param slot       槽位
     * @param port       端口
     * @param cover_vlan 外层VLAN号
     * @param inner_vlan 内层VLAN号
     * @param count      数量
     */
    public static void CreateC600GPONV2(Integer slot, Integer port, Integer cover_vlan, Integer inner_vlan, Integer count, List<String> loidList,List<String> desList) throws IOException {

        if(loidList.size()!=desList.size()){
            System.out.println("loid与desc不匹配，请检查");
        }else {


            String FileName = "F:\\Creator\\C600GPON_" + slot + "_" + port + "_";
            Calendar cal = Calendar.getInstance();
            FileName += cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
            FileName += ".txt";
            FileUtils.CreatePWwriter(FileName);



            FileUtils.writeLine("第一部分：添加ONU\r\n");
            FileUtils.writeLine("interface gpon_olt-1/" + slot + "/" + port);

            for (int i = 1; i <= count; i++) {
                int index = i;
                FileUtils.writeLine("onu " + i + "  type FTTH_GPON_HGU loid"+" "+loidList.get(--index));
            }

            FileUtils.writeLine("\r\n");
            FileUtils.writeLine("第二部分：配置onu上行模板\r\n");

            for (int i = 1; i <= count; i++) {
                String part2 = "\r\n" +
                        "interface gpon_onu-1/" + slot + "/" + port + ":" + i + "\r\n" +
                        "  tcont 1 name tcont-1 profile HG_300M\r\n" +
                        "  sn-bind disable\r\n" +
                        "  gemport 1 name gemport-1 tcont 1\r\n" +
                        "$";
                FileUtils.writeLine(part2);
            }


            FileUtils.writeLine("\r\n");
            FileUtils.writeLine("第三部分：配置onu的扩展属性\r\n");
            for (int i = 1; i <= count; i++) {
                String part3 = "pon-onu-mng gpon_onu-1/" + slot + "/" + port + ":" + i + "\r\n" +
                        "  vlan port veip_1 mode trunk\r\n" +
                        "  vlan port veip_1 vlan 41,45-46\r\n" +
                        "  mvlan 50\r\n" +
                        "  service eth_service1 gemport 1 vlan 41\r\n" +
                        "  service eth_service2 gemport 1 vlan 45\r\n" +
                        "  service eth_service3 gemport 1 vlan 46\r\n" +
                        "$";
                FileUtils.writeLine(part3);
            }


            FileUtils.writeLine("\r\n");
            FileUtils.writeLine("第四部分：配置onu的业务vlan\r\n");
            for (int i = 1; i <= count; i++) {
                String part4 = "interface vport-1/" + slot + "/" + port + "." + i + ":1\r\n" +
                        "  service-port 1 user-vlan 46 vlan 46 svlan " + cover_vlan + "\r\n" +
                        "  service-port 2 user-vlan 41 vlan " + (inner_vlan++) + " svlan " + cover_vlan + "\r\n" +
                        "  service-port 3 user-vlan 45 vlan 45 svlan " + cover_vlan + "\r\n" +
                        "  qos traffic-policy HG_1000M direction egress\r\n" +
                        "  qos traffic-policy HG_300M direction ingress\r\n" +
                        "  igmp fast-leave enable\r\n" +
                        "$";
                FileUtils.writeLine(part4);
            }


            FileUtils.writeLine("\r\n");
            FileUtils.writeLine("第五部分：配置onu的IPTV组播\r\n");

            FileUtils.writeLine("igmp mvlan 50\r\n");

            for (int i = 1; i <= count; i++) {
                String part5 = "receive-port vport-1/" + slot + "/" + port + "." + i + ":1";
                FileUtils.writeLine(part5);
            }

            System.out.println("生成命令成功，文件位置：" + FileName);


        }



    }









    public static void main(String[] args) throws IOException {

        List<String> loidList = ReadIn("F:\\a\\loid.txt");
        List<String> descList = ReadIn("F:\\a\\desc.txt");

        CreateC600GPON(3, 2, 2504, 359, 41,loidList,descList);

//        CreateC600EPON(12, 3, 2300, 440, 40);
    }

}
