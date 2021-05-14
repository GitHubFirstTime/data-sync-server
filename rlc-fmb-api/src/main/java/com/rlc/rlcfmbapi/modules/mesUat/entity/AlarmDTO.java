package com.rlc.rlcfmbapi.modules.mesUat.entity;


import com.rlc.rlcbase.persistence.DataEntity;

public class AlarmDTO extends DataEntity<AlarmDTO> {
    //告警编号
    private String alarmId;
    //设备名称
    private String deviceName;
    //设备类型
    private String deviceType;
    //设备类型名称
    private String deviceTypeName;
    //告警代码
    private String alarmReasonCode;
    //告警状态
    private String alarmStatus;
    //告警等级
    private String alarmLevel;
    //告警日期
    private String alarmTime;
    //告警系统 mes 或者 eap
    private String alarmType;
    //告警详情
    private String alarmDescription;
    //总条数
    private String total;

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

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

    public String getAlarmReasonCode() {
        return alarmReasonCode;
    }

    public void setAlarmReasonCode(String alarmReasonCode) {
        this.alarmReasonCode = alarmReasonCode;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmDescription() {
        return alarmDescription;
    }

    public void setAlarmDescription(String alarmDescription) {
        this.alarmDescription = alarmDescription;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "AlarmDTO{" +
                "alarmId='" + alarmId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", alarmReasonCode='" + alarmReasonCode + '\'' +
                ", alarmStatus='" + alarmStatus + '\'' +
                ", alarmLevel='" + alarmLevel + '\'' +
                ", alarmTime='" + alarmTime + '\'' +
                ", alarmType='" + alarmType + '\'' +
                ", alarmDescription='" + alarmDescription + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
