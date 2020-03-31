package com.tracy.utils;

public class StringUtils {


    public static String FormatResult() {

        String result = null;

        String Msg = "短信";
        String MsgContent = "流量超60%预警：合计1个乡镇，其中金城江0个、都安0个、宜州0个、大化0个、南丹0个、环江0个、巴马0个、东兰0个、罗城1个、天峨0个、凤山0个。";

        String OAReport = "（2）接入层流量预警情况：";
        String OAContentCity = "①城市：截止3月30日8:30，城区有0台OLT上行电路峰值流量利用率超出60%的预警值警：其中金城江0台、都安0台、宜州0台、大化0台、南丹0台、环江0台、巴马0台、东兰0台、罗城0台、天峨0台、凤山0台。";
        String OAContentCountryside = "②农村：截止3月30日8:30，全市138个乡镇，有3台OLT上行电路、1个乡镇出口电路峰值流量利用率超出60%的预警值警：其中OLT预警的有金城江1台、都安0台、宜州0台、大化0台、南丹1台、环江0台、巴马0台、东兰0台、罗城0台、天峨1台、凤山0台。乡镇出口预警的有金城江0个、都安0个、宜州0个、大化0个、南丹0个、环江0个、巴马0个、东兰0个、罗城1个、天峨0个、凤山0个。";

        String[] MsgContentSplit = OAContentCountryside.split("、");
        for (int i = 0; i < MsgContentSplit.length; i++) {
            if (MsgContentSplit[i].contains("0台")) {


            } else if (MsgContentSplit[i].contains("0个")) {

            } else {
                System.out.println(MsgContentSplit[i] + "\n");
            }

        }

        return result;
    }


    public static void main(String[] args) {
        FormatResult();
    }

}
