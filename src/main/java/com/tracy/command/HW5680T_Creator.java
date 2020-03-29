package com.tracy.command;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Calendar;

import com.tracy.utils.FileUtils;
import org.w3c.dom.ls.LSOutput;


/**
 * Created by trcay on 2019/8/17.
 */
public class HW5680T_Creator {

    
	/**
	 * function:生成41、45、46通道的ServicePort,并写入文件
     * @param Svlan     外层Vlan
     * @param InnerVlan 内层Vlan
     * @param Slot      槽位
     * @param Port      端口
	 * @param Index41   41索引
	 * @param Index45   45索引
	 * @param Index46   46索引
	 * @param Num       数量
	 * @throws IOException
	 */
    public static void CreateServicePort(Integer Svlan, Integer InnerVlan, Integer Slot, Integer Port,Integer Index41,Integer Index45,Integer Index46, Integer Num) throws IOException {

        String FileName = "F:\\Creator\\command_";
        Calendar cal = Calendar.getInstance();
        FileName+=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH);
        FileName+=".txt";
        FileUtils.CreatePWwriter(FileName);

        for (int i = 0; i < Num; i++) {

            String Pass41 = "service-port  vlan " + Svlan + " gpon 0/" + Slot + "/" + Port + " ont " + i + " gemport 0 multi-service " +
                    "user-vlan 41 tag-transform translate-and-add inner-vlan " + InnerVlan + " inner-priority 2 inbound " +
                    "traffic-table index "+Index41+" outbound traffic-table index "+Index41;
            FileUtils.writeLine(Pass41);

            String Pass45 = "service-port  vlan " + Svlan + " gpon 0/" + Slot + "/" + Port + " ont " + i + " gemport 0 multi-service " +
                    "user-vlan 45 tag-transform translate-and-add inner-vlan 45 inner-priority 5 inbound " +
                    "traffic-table index "+Index45+" outbound traffic-table index "+Index45;
            FileUtils.writeLine(Pass45);

            String Pass46 = "service-port  vlan " + Svlan + " gpon 0/" + Slot + "/" + Port + " ont " + i + " gemport 0 multi-service " +
                    "user-vlan 46 tag-transform translate-and-add inner-vlan 46 inner-priority 7 inbound " +
                    "traffic-table index "+Index46+" outbound traffic-table index "+Index46;
            FileUtils.writeLine(Pass46);

            InnerVlan++;
        }
    }

    /**
     * 
     * @param Port  端口
     * @param ont_lineprofile_id  线路模版id，每台olt可能不同
     * @param ont_srvprofile_id   service模版id，每台olt可能不同
     * @param profile_id          模版id,每台olt可能不同
     * @param num
     */
    public static void CreateOntAdd(Integer Port , Integer ont_lineprofile_id,Integer ont_srvprofile_id,Integer profile_id,int num) {

        System.out.println("port " + Port + " ont-auto-find enable");

        for (int i = 0; i < num; i++) {

            String ontAdd = "ont add " + Port + " " + i + " loid-auth " + "-----LogicId------" + " always-on omci ont-lineprofile-id "+ont_lineprofile_id+" ont-srvprofile-id " +
            		+ont_srvprofile_id+" desc " + " description ";

            FileUtils.writeLine(ontAdd);

            String tcont = "tcont bind-profile " + Port + " " + i + " 1 profile-id "+profile_id+"";

            FileUtils.writeLine(tcont);


            FileUtils.writeLine("注：因5680T的service port是随机的，所以iptv组播流需单独添加...");


/*          组播流
            btv
            igmp encapsulation IP
                    btv
            igmp user add service-port 8040 no-auth
            igmp user add service-port 11412 no-auth
            igmp user add service-port 10882 no-auth
            igmp user add service-port 10416 no-auth
            igmp user add service-port 8647 no-auth
            igmp user add service-port 10618 no-auth
            igmp user add service-port 8054 no-auth
            igmp user add service-port 7652 no-auth
            igmp user add service-port 12034 no-auth

                */
        }
    }


    public static void main(String[] args) throws IOException {

        //注：loid与description当前程序没有读入，使用UE的列模式修改，待后续完善程序再加入此功能

    	//参数顺序：外层vlan，内层vlan，槽位，端口，41索引，45索引，46索引，生成数量（从开始）
        CreateServicePort(3301, 201, 6, 0,11,12,13,25);
        System.out.println();
        //参数顺序：端口，ont_lineprofile_id,ont_srvprofile_id,profile_id,数量
        CreateOntAdd(6,15,15,50,25);
        System.out.println("生成成功");
        FileUtils.closeWriter();

    }

}
