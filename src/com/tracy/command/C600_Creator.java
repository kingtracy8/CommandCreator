package com.tracy.command;

import com.tracy.utils.FileUtils;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by trcay on 2019/9/18.
 */
public class C600_Creator {


    /**
     * function: 生成GPON脚本
     *
     * @param slot       槽位
     * @param port       端口
     * @param cover_vlan 外层VLAN号
     * @param inner_vlan 内层VLAN号
     * @param count      数量
     */
    public static void CreateC600GPON(Integer slot, Integer port, Integer cover_vlan, Integer inner_vlan, Integer count) throws IOException {


        System.out.println("第一部分：添加ONU");


        System.out.println("interface gpon_olt-1/" + slot + "/" + port);

        for (int i = 1; i <= count; i++) {
            System.out.println("onu " + i + "  type FTTH_GPON_HGU loid");
        }

        System.out.println();

        System.out.println("第二部分：配置onu上行模板");


        for (int i = 1; i <= count; i++) {
            System.out.println("\n" +
                    "interface gpon_onu-1/" + slot + "/" + port + ":" + i + "\n" +
                    "  tcont 1 name tcont-1 profile HG_300M\n" +
                    "  sn-bind disable\n" +
                    "  gemport 1 name gemport-1 tcont 1\n" +
                    "$");

        }

        System.out.println();

        System.out.println("第三部分：配置onu的扩展属性");


        for (int i = 1; i <= count; i++) {
            System.out.println("pon-onu-mng gpon_onu-1/" + slot + "/" + port + ":" + i + "\n" +
                    "  vlan port veip_1 mode trunk\n" +
                    "  vlan port veip_1 vlan 41,45-46\n" +
                    "  mvlan 50\n" +
                    "  service eth_service1 gemport 1 vlan 41\n" +
                    "  service eth_service2 gemport 1 vlan 45\n" +
                    "  service eth_service3 gemport 1 vlan 46\n" +
                    "$");

        }

        System.out.println();

        System.out.println("第四部分：配置onu的业务vlan");

        for (int i = 1; i <= count; i++) {
            System.out.println("interface vport-1/" + slot + "/" + port + "." + i + ":1\n" +
                    "  service-port 1 user-vlan 46 vlan 46 svlan " + cover_vlan + "\n" +
                    "  service-port 2 user-vlan 41 vlan " + (inner_vlan++) + " svlan " + cover_vlan + "\n" +
                    "  service-port 3 user-vlan 45 vlan 45 svlan " + cover_vlan + "\n" +
                    "  qos traffic-policy HG_1000M direction egress\n" +
                    "  qos traffic-policy HG_300M direction ingress\n" +
                    "  igmp fast-leave enable\n" +
                    "$");
        }


        System.out.println();
        System.out.println("第五部分：配置onu的IPTV组播");

        System.out.println();

        System.out.println("igmp mvlan 50");


        for (int i = 1; i <= count; i++) {
            System.out.println("receive-port vport-1/" + slot + "/" + port + "." + i + ":1");
        }

    }


    /**
     * @param slot       槽位
     * @param port       端口
     * @param cover_vlan 外层VLAN号
     * @param inner_vlan 内层VLAN号
     * @param count      数量
     */
    public static void CreateC600EPON(Integer slot, Integer port, Integer cover_vlan, Integer inner_vlan, Integer count) {

        System.out.println("第一部分：添加ONU");
        System.out.println();

        System.out.println("interface gpon_olt-1/" + slot + "/" + port);

        for (int i = 1; i <= count; i++) {

            System.out.println("onu " + i + " type FTTH_EPON_HGU loid ");
        }

        System.out.println();
        System.out.println("第二部分:配置onu模板");
        System.out.println();
        for (int i = 1; i <= count; i++) {

            System.out.println("interface epon_onu-1/" + slot + "/" + port + ":" + i + "\n" +
                    " description                     \n" +
                    "  sla-profile HG_UP300M_DW1000M vport 1\n" +
                    " $\n" +
                    " ");
        }


        System.out.println();
        System.out.println("第三部分：配置onu vlan");
        System.out.println();
        for (int i = 1; i <= count; i++) {

            System.out.println("interface vport-1/" + slot + "/" + port + "." + i + ":1\n" +
                    "  service-port 1 user-vlan 46 vlan 46 svlan " + cover_vlan + "\n" +
                    "  service-port 2 user-vlan 41 vlan " + (inner_vlan++) + " svlan " + cover_vlan + "\n" +
                    "  service-port 3 user-vlan 45 vlan 45 svlan " + cover_vlan + "\n" +
                    "  qos traffic-policy HG_1000M direction egress\n" +
                    "  igmp fast-leave enable\n" +
                    " $\n" +
                    "  ");
        }


        System.out.println();
        System.out.println("第四部分：配置onu的iptv组播");
        System.out.println();
        System.out.println("igmp mvlan 50");

        for (int i = 1; i <= count; i++) {
            System.out.println("receive-port vport-1/" + slot + "/" + port + "." + i + ":1");
        }

    }


    public static void main(String[] args) throws IOException {
//        CreateC600GPON(1, 8, 2500, 957, 10);

        CreateC600EPON(2, 1, 2301, 200, 5);
    }

}
