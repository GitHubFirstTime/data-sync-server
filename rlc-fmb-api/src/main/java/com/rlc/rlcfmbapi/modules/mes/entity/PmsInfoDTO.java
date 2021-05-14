package com.rlc.rlcfmbapi.modules.mes.entity;


import com.rlc.rlcbase.persistence.DataEntity;


public class PmsInfoDTO extends DataEntity<PmsInfoDTO> {

    //设备名称
    private String deviceName;
    //设备类型
    private String deviceType;
    //设备类型名称
    private String deviceTypeName;
    //维护类型
    private String maintenceType;
    //维护日期
    private String maintenceTime;
    //下次维护日期
    private String nextMaintenceTime;
    //维保ID
    private String pmsId;
    //维保材料
    private String maintenceMaterial;
    //总条数
    private String total;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getMaintenceType() {
        return maintenceType;
    }

    public void setMaintenceType(String maintenceType) {
        this.maintenceType = maintenceType;
    }

    public String getMaintenceTime() {
        return maintenceTime;
    }

    public void setMaintenceTime(String maintenceTime) {
        this.maintenceTime = maintenceTime;
    }

    public String getNextMaintenceTime() {
        return nextMaintenceTime;
    }

    public void setNextMaintenceTime(String nextMaintenceTime) {
        this.nextMaintenceTime = nextMaintenceTime;
    }

    public String getPmsId() {
        return pmsId;
    }

    public void setPmsId(String pmsId) {
        this.pmsId = pmsId;
    }

    public String getMaintenceMaterial() {
        return maintenceMaterial;
    }

    public void setMaintenceMaterial(String maintenceMaterial) {
        this.maintenceMaterial = maintenceMaterial;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PmsInfoDTO{" +
                "deviceName='" + deviceName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", maintenceType='" + maintenceType + '\'' +
                ", maintenceTime='" + maintenceTime + '\'' +
                ", nextMaintenceTime='" + nextMaintenceTime + '\'' +
                ", pmsId='" + pmsId + '\'' +
                ", maintenceMaterial='" + maintenceMaterial + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
