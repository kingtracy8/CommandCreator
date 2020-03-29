package com.tracy.command;

import javax.lang.model.type.IntersectionType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tracy.utils.ReadFormTXT.ReadIn;

public class IMS_Creator {

    public static void Creator_Node(Integer start_Node, String voip, Integer startIp, String gateway, Integer vlanId, Integer trunk, Integer count) {

        List<String> nameList = Arrays.asList("巴马-宏捷勾机-F820", "康柏大酒店背后", "巴马-城北电信支局办公", "广电局FTTB龙超良家后面", "巴马-公路小区办公楼-1F820-4槽位", "巴马-公路小区路边上头F820", "公路小区中间第5排");


        Integer temp_ip = startIp;

        for (int i = 1; i <= count; i++) {

            if (nameList.size() == count) {
                String IP = voip + "" + startIp;
                Integer j = i;
                String Ims_data = "ADD IAD:NODEID=" + start_Node + ",NAME=\"" + nameList.get(--j) + "\",IPADDRESS=\"" + IP + "\",LOCPORT=2944,REMPORT=2944,HOSTNAME=\"" + IP + "\",DOMAINNAME=\"GX.CTCIMS.CN\";\n" +
                        "ADD H248 XGCFNODEATTR:NODEID=" + start_Node + ",MGSCFGID=1,ECSWITCH=\"1\",PANIID=0,LINKTESTERRCODE=0;                                                                           \n" +
                        "ADD H248 XGCFTIDPROPERTY:NODEID=" + start_Node + ",TERMTYPE=\"RTP\",PROPERTYID=1,PACKAGEID=1;                                                                                  \n" +
                        "ADD H248 XGCFTIDPROPERTY:NODEID=" + start_Node + ",TERMTYPE=\"USER\",PROPERTYID=1,PACKAGEID=1;";

                System.out.println(Ims_data);
                System.out.println();
                String Bars_data = "static-user " + IP + " " + IP + " gateway " + gateway + " vpn-instance CT-softswitch-yw interface Eth-Trunk" + trunk + ".51 vlan " + vlanId + " domain-name softswitch-vpn detect";
//                System.out.println(Bars_data);
                System.out.println();

                start_Node++;
                startIp++;

            }


        }


        for (int i = 1; i <= count; i++) {


            if (nameList.size() == count) {
                String IP = voip + "" + temp_ip;
                Integer j = i;

                String Bars_data = "static-user " + IP + " " + IP + " gateway " + gateway + " vpn-instance CT-softswitch-yw interface Eth-Trunk" + trunk + ".51 vlan " + vlanId + " domain-name softswitch-vpn detect";
                System.out.println(Bars_data);
                System.out.println();

                start_Node++;
                temp_ip++;

            }
        }

    }

    /**
     * @param ipList   语音ip
     * @param nodeList 节点号
     * @param nameList FTTB设备名称
     * @param gateway  语音网关
     * @param vlan     语音VLAN
     * @param trunk    聚合组
     */
    public static void CreateNodeV2(List<String> ipList, List<String> nodeList, List<String> nameList, String gateway, Integer vlan, Integer trunk) {

        if (ipList.size() != nodeList.size() || nodeList.size() != nameList.size()) {
            System.out.println("节点与设备匹配错误，检查脚本");
        } else {
            for (int i = 0; i < ipList.size(); i++) {


                //1、生成IMS脚本
                String ims_data = "ADD IAD:NODEID=" + nodeList.get(i) + ",NAME=\"" + nameList.get(i) + "\",IPADDRESS=\"" + ipList.get(i) + "\",LOCPORT=2944,REMPORT=2944,HOSTNAME=\"" + ipList.get(i) + "\",DOMAINNAME=\"GX.CTCIMS.CN\";\n" +
                        "ADD H248 XGCFNODEATTR:NODEID=" + nodeList.get(i) + ",MGSCFGID=1,ECSWITCH=\"1\",PANIID=0,LINKTESTERRCODE=0;                                                                           \n" +
                        "ADD H248 XGCFTIDPROPERTY:NODEID=" + nodeList.get(i) + ",TERMTYPE=\"RTP\",PROPERTYID=1,PACKAGEID=1;                                                                                  \n" +
                        "ADD H248 XGCFTIDPROPERTY:NODEID=" + nodeList.get(i) + ",TERMTYPE=\"USER\",PROPERTYID=1,PACKAGEID=1;";

                System.out.println(ims_data);

                System.out.println();

            }



            for (int i = 0; i < ipList.size(); i++) {

                    String Bars_data = "static-user " + ipList.get(i) + " " + ipList.get(i) + " gateway " + gateway + " vpn-instance CT-softswitch-yw interface Eth-Trunk" + trunk + ".51 vlan " + vlan + " domain-name softswitch-vpn detect";
                    System.out.println(Bars_data);
                    System.out.println();


                }




        }


    }


    public static void main(String[] args) {

//        Creator_Node(180900226, "5.175.81.", 103, "5.175.81.1", 3000, 15, 7);

        List<String> ipList = ReadIn("F:\\a\\ip.txt");
        List<String> nodeList = ReadIn("F:\\a\\node.txt");
        List<String> nameList = ReadIn("F:\\a\\name.txt");

        CreateNodeV2(ipList, nodeList, nameList, "5.175.81.1", 3300, 2);

    }


}
