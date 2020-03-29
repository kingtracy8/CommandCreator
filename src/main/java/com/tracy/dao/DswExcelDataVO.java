package com.tracy.dao;

/**
 * Created by trcay on 2020/3/29.
 * Description:汇聚交换机(DSW)报表读取对象
 */
public class DswExcelDataVO {

    private String electricName;

    private String deviceA;

    private String interfaceA;

    private String deviceB;

    private String interfaceB;

    private String bandwidth;

    private String dayEveOutRatio;

    private String eveOutSpeed;


    public String getElectricName() {
        return electricName;
    }

    public void setElectricName(String electricName) {
        this.electricName = electricName;
    }

    public String getDeviceA() {
        return deviceA;
    }

    public void setDeviceA(String deviceA) {
        this.deviceA = deviceA;
    }

    public String getInterfaceA() {
        return interfaceA;
    }

    public void setInterfaceA(String interfaceA) {
        this.interfaceA = interfaceA;
    }

    public String getDeviceB() {
        return deviceB;
    }

    public void setDeviceB(String deviceB) {
        this.deviceB = deviceB;
    }

    public String getInterfaceB() {
        return interfaceB;
    }

    public void setInterfaceB(String interfaceB) {
        this.interfaceB = interfaceB;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getDayEveOutRatio() {
        return dayEveOutRatio;
    }

    public void setDayEveOutRatio(String dayEveOutRatio) {
        this.dayEveOutRatio = dayEveOutRatio;
    }

    public String getEveOutSpeed() {
        return eveOutSpeed;
    }

    public void setEveOutSpeed(String eveOutSpeed) {
        this.eveOutSpeed = eveOutSpeed;
    }
}
